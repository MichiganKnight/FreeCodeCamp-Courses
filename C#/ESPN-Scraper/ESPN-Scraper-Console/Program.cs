using ESPN_Scraper_Console.Models;
using ESPN_Scraper_Console.Services;

namespace ESPN_Scraper_Console
{
    public static class Program
    {
        private static async Task Main(string[] args)
        {
            try
            {
                using HttpClient client = new();
                EspnApiService espn = new(client);

                await PrintGameStats(espn);
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private static async Task PrintGameStats(EspnApiService espn)
        {
            Scoreboard? scoreboard = await espn.GetScoreboardAsync();
            if (scoreboard?.Events != null)
            {
                foreach (Event ev in scoreboard.Events)
                {
                    Console.WriteLine($"Game: {ev.Name} (ID: {ev.Id})");
                }
            }

            // Example: Pick Game ID
            string gameId = scoreboard?.Events?.FirstOrDefault()?.Id ?? "";
            if (!string.IsNullOrEmpty(gameId))
            {
                Console.WriteLine($"\n--- Summary for Game: {gameId} ---");

                GameSummary? summary = await espn.GetGameSummaryAsync(gameId);

                // Print All Starter Stats
                PrintPlayerCentricStats(summary!);
            }
        }

        private static readonly Dictionary<string, string[]> AllowedCategories = new()
        {
            { "Quarterback", new[] { "passing", "rushing" } },
            { "Running Back", new[] { "rushing", "receiving" } },
            { "Receiver", new[] { "receiving", "rushing" } },
            { "Kicker", new[] { "kicking" } },
            { "Other", Array.Empty<string>() }
        };

        private static void PrintPlayerCentricStats(GameSummary summary)
        {
            if (summary?.BoxScore?.Players == null || summary.BoxScore.Players.Count == 0) return;

            foreach (PlayerStats team in summary.BoxScore.Players)
            {
                Console.WriteLine($"\n=== Team: {team.Team?.DisplayName} ===");

                if (team.Statistics == null) continue;

                Dictionary<string, (string Name, Dictionary<string, string> Stats, string Position)> playersDict =
                    new Dictionary<string, (string Name, Dictionary<string, string> Stats, string Position)>();

                foreach (StatCategory category in team.Statistics)
                {
                    if (category.Athletes == null || category.Athletes.Count == 0) continue;

                    string categoryName = category?.Name?.ToLower();

                    string positionLabel = categoryName switch
                    {
                        "passing" => "Quarterback",
                        "rushing" => "Running Back",
                        "receiving" => "Receiver",
                        "kicking" => "Kicker",
                        _ => "Other"
                    };

                    if (!AllowedCategories[positionLabel].Contains(categoryName)) continue;

                    foreach (AthleteStats athlete in category?.Athletes)
                    {
                        string athleteId = athlete.Athlete?.Id ?? Guid.NewGuid().ToString();
                        if (!playersDict.ContainsKey(athleteId))
                        {
                            playersDict[athleteId] = (athlete.Athlete?.DisplayName ?? "Unknown",
                                new Dictionary<string, string>(), positionLabel);
                        }

                        Dictionary<string, string> statsDict = playersDict[athleteId].Stats;

                        if (athlete.Stats != null && category.Labels != null)
                        {
                            for (int i = 0; i < category.Labels.Count && i < athlete.Stats.Count; i++)
                            {
                                string label = category.Labels[i];

                                string friendlyLabel = label switch
                                {
                                    "C/ATT" => "Completions / Attempts",
                                    "YDS" => categoryName == "passing" ? "Passing Yards" :
                                        categoryName == "rushing" ? "Rushing Yards" :
                                        categoryName == "receiving" ? "Receiving Yards" : "Yards",
                                    "AVG" => categoryName == "passing" ? "Average Passing Yards" :
                                        categoryName == "rushing" ? "Average Rushing Yards" : "Average Yards",
                                    "TD" => categoryName == "passing" ? "Passing Touchdowns" :
                                        categoryName == "rushing" ? "Rushing Touchdowns" :
                                        categoryName == "receiving" ? "Receiving Touchdowns" : "TD",
                                    "INT" => "Interceptions",
                                    "QBR" => "QBR",
                                    "RTG" => "Rating",
                                    "FUM" => "Fumbles",
                                    "CAR" => "Carries",
                                    "LONG" => "Longest Play",
                                    "TGTS" => "Targets",
                                    "REC" => "Receptions",
                                    "FG" => "Field Goals",
                                    "FGM" => "Field Goals Made",
                                    "FGA" => "Field Goals Attempted",
                                    "PCT" => "Field Goal Percentage",
                                    "PAT" => "PAT Made",
                                    "XP" => "Extra Points Made",
                                    "PTS" => "Points",
                                    "SACKS" => "Sacks",
                                    _ => label
                                };

                                statsDict[friendlyLabel] = athlete.Stats[i];
                            }
                        }
                    }
                }

                // Print each player
                foreach ((string Name, Dictionary<string, string> Stats, string Position) player in playersDict.Values)
                {
                    Console.WriteLine($"\n{player.Position}: {player.Name}");
                    foreach (KeyValuePair<string, string> kvp in player.Stats)
                    {
                        Console.WriteLine($"  {kvp.Key}: {kvp.Value}");
                    }
                }
            }
        }
    }
}