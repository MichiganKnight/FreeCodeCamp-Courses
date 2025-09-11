using System.Text;
using Microsoft.AspNetCore.Mvc;
using Sports_API.Areas.NFLScraper.Models;

namespace Sports_API.Areas.NFLScraper.Controllers
{
    [Area("NFLScraper")]
    public class TeamController : Controller
    {
        [HttpGet]
        [ActionName("NFLTeam")]
        public async Task<ActionResult> NFLTeam(string? team, string? abbr, string? include = null, string? exclude = null)
        {
            ViewBag.TeamName = team;
            
            using HttpClient client = new();
            NflService nfl = new(client);

            TeamModel? teamModel = await nfl.GetTeamStatsAsync(abbr);
            List<TeamStats> model = [];
            
            if (teamModel?.Team != null)
            {
                ViewBag.TeamColor = teamModel.Team.Color;
                
                if (teamModel?.Team?.Record?.Items != null)
                {
                    foreach (Item item in teamModel.Team.Record.Items)
                    {
                        if (item.TeamStats == null) continue;
                            
                        foreach (TeamStats stat in item.TeamStats)
                        {
                            model.Add(stat);
                        }
                    }
                }
            }

            List<string> includeKeysList = ParseList(include);
            HashSet<string> excludeKeys = new(ParseList(exclude), StringComparer.OrdinalIgnoreCase);

            if (includeKeysList.Count == 0)
            {
                includeKeysList =
                [
                    "wins", "losses", "ties",
                    "winPercent", "OTWins", "OTLosses",
                    "avgPointsFor", "avgPointsAgainst", "differential"
                ];
            }
            
            Dictionary<string, string> byKey = new(StringComparer.OrdinalIgnoreCase);
            
            foreach (TeamStats teamStat in model)
            {
                if (string.IsNullOrWhiteSpace(teamStat?.Name)) continue;
                
                byKey[teamStat.Name!] = teamStat.Value ?? string.Empty;
            }
            
            if (!excludeKeys.Contains("differential"))
            {
                if (byKey.TryGetValue("avgPointsFor", out string? apfStr) &&
                    byKey.TryGetValue("avgPointsAgainst", out string? apaStr) &&
                    double.TryParse(apfStr, out double apf) &&
                    double.TryParse(apaStr, out double apa))
                {
                    double diff = apf - apa;
                    byKey["differential"] = diff.ToString("0.0");
                }
            }
            
            List<TeamStats> filtered = [];
            foreach (string key in includeKeysList)
            {
                if (excludeKeys.Contains(key)) continue;

                // differential may be computed above; others come from byKey
                if (!byKey.TryGetValue(key, out string? val)) continue;

                filtered.Add(new TeamStats
                {
                    Name = ToDisplayLabel(key),
                    Value = val
                });
            }

            ViewBag.SelectedIncludes = string.Join(", ", includeKeysList);
            ViewBag.SelectedExcludes = string.Join(", ", excludeKeys);
            
            return View(filtered);
        }

        private static readonly Dictionary<string, string> LabelOverrides = new(StringComparer.OrdinalIgnoreCase)
        {
            { "wins", "Wins" },
            { "losses", "Losses" },
            { "ties", "Ties" },
            { "winPercent", "Win %" },
            { "OTWins", "Overtime Wins" },
            { "OTLosses", "Overtime Losses" },
            { "avgPointsFor", "Average Points For" },
            { "avgPointsAgainst", "Average Points Against" },
            { "differential", "Differential" }
        };

        private static string ToDisplayLabel(string key)
        {
            if (LabelOverrides.TryGetValue(key, out string? label))
            {
                return label;
            }

            StringBuilder result = new();
            for (int i = 0; i < key.Length; i++)
            {
                char c = key[i];
                
                bool newWord = 
                    i == 0 ||
                    (char.IsUpper(c) && char.IsLower(key[i - 1])) ||
                    (char.IsLetterOrDigit(c) && !char.IsLetterOrDigit(key[i - 1]));

                if (newWord && result.Length > 0)
                {
                    result.Append(' ');
                }
                
                result.Append(i == 0 ? char.ToUpper(c) : c);
            }
            
            return result.ToString();
        }

        private static string FromDisplayLabelToKeyGuess(string? display)
        {
            if (string.IsNullOrWhiteSpace(display)) return string.Empty;

            foreach (KeyValuePair<string, string> kvp in LabelOverrides.Where(kvp => string.Equals(kvp.Value, display, StringComparison.OrdinalIgnoreCase)))
            {
                return kvp.Key;
            }
            
            return display.Replace(" ", "", StringComparison.OrdinalIgnoreCase);
        }

        private static List<string> ParseList(string? csv)
        {
            return string.IsNullOrWhiteSpace(csv) ? [] : csv.Split(',', StringSplitOptions.RemoveEmptyEntries | StringSplitOptions.TrimEntries).ToList();
        }

        private static int IndexOfIgnoreCase(List<string> list, string? value)
        {
            if (string.IsNullOrEmpty(value)) return int.MaxValue;
            for (int i = 0; i < list.Count; i++)
            {
                if (string.Equals(list[i], value, StringComparison.OrdinalIgnoreCase))
                {
                    return i;
                }
            }
            
            return int.MaxValue;

        }
    }
}