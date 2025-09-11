using Newtonsoft.Json;
using Sports_API.Areas.CFBScraper.Models;

namespace Sports_API.Areas.CFBScraper
{
    public class CfbService
    {
        private HttpClient _client;
        
        public CfbService(HttpClient client)
        {
            _client = client;
        }
        
        // Get NFL Teams
        public async Task<CFBTeamsModel?> GetTeamsAsync()
        {
            const string url = "https://site.api.espn.com/apis/site/v2/sports/football/college-football/teams";
            string json = await _client.GetStringAsync(url);

            return JsonConvert.DeserializeObject<CFBTeamsModel>(json);
        }
        
        // Get Specific Team Stats
        public async Task<CFBTeamModel?> GetTeamStatsAsync(string? teamAbbr)
        {
            string url = $"https://site.api.espn.com/apis/site/v2/sports/football/college-football/teams/{Uri.EscapeDataString(teamAbbr!)}";
            string json = await _client.GetStringAsync(url);
            
            return JsonConvert.DeserializeObject<CFBTeamModel>(json);
        }
    }
}