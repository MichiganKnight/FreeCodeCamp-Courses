using System.Text;
using Microsoft.AspNetCore.Mvc;
using Sports_API.Areas.NFLScraper.Models;
using static System.Int32;

namespace Sports_API.Areas.NFLScraper.Controllers
{
    [Area("NFLScraper")]
    public class TeamController : Controller
    {
        private readonly IWebHostEnvironment _env;
        
        public TeamController(IWebHostEnvironment env)
        {
            _env = env;
        }
        
        [HttpGet]
        [ActionName("NFLTeam")]
        public async Task<ActionResult> NFLTeam(string? team, string? abbr, string? include = null, string? exclude = null)
        {
            ViewBag.TeamName = team;
            ViewBag.FaviconPath = Path.Combine(_env.WebRootPath, $"/favicons/{team}.ico");
            
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

                        ViewBag.Record = item.Summary;
                            
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
                    "winPercent",
                    "OTWins", "OTLosses",
                    "pointsFor", "differential", "pointsAgainst",
                    "avgPointsFor", "avgPointsAgainst"
                ];
            }
            
            Dictionary<string, string> byKey = new(StringComparer.OrdinalIgnoreCase);
            
            foreach (TeamStats teamStat in model.Where(teamStat => !string.IsNullOrWhiteSpace(teamStat?.Name)))
            {
                byKey[teamStat.Name!] = teamStat.Value ?? string.Empty;
            }
            
            if (byKey.TryGetValue("winPercent", out string? rawWinPct))
            {
                if (double.TryParse(rawWinPct, out double pct0To1) && pct0To1 is >= 0 and <= 1.000_000_1)
                {
                    byKey["winPercent"] = (pct0To1 * 100).ToString("0.00") + "%";
                }
            }
            
            if (!excludeKeys.Contains("winPercent"))
            {
                if (byKey.TryGetValue("wins", out string? wStr) &&
                    byKey.TryGetValue("losses", out string? lStr))
                {
                    TryParse(wStr, out int w);
                    TryParse(lStr, out int l);
                    
                    int t = 0;
                    
                    if (byKey.TryGetValue("ties", out string? tStr)) TryParse(tStr, out t);

                    int total = w + l + t;
                    if (total > 0)
                    {
                        double pct0To1 = (w + 0.5 * t) / total;
                        byKey["winPercent"] = (pct0To1 * 100).ToString("0.00") + "%";
                    }
                }
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

                if (!byKey.TryGetValue(key, out string? val)) continue;

                if (string.Equals(key, "winPercent", StringComparison.OrdinalIgnoreCase))
                {
                    if (!val.EndsWith('%'))
                    {
                        if (double.TryParse(val, out var pct0To1) && pct0To1 >= 0 && pct0To1 <= 1.000_000_1)
                        {
                            val = (pct0To1 * 100).ToString("0.00") + "%";
                        }
                    }
                }

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
            { "pointsFor", "Points For" },
            { "differential", "Point Differential" },
            { "pointsAgainst", "Points Against"},
            { "avgPointsFor", "Average Points For" },
            { "avgPointsAgainst", "Average Points Against" }
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

        private static List<string> ParseList(string? csv)
        {
            return string.IsNullOrWhiteSpace(csv) ? [] : csv.Split(',', StringSplitOptions.RemoveEmptyEntries | StringSplitOptions.TrimEntries).ToList();
        }
    }
}