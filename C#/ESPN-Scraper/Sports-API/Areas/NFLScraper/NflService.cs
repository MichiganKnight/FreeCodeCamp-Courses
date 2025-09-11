using Newtonsoft.Json;
using Sports_API.Areas.NFLScraper.Models;

namespace Sports_API.Areas.NFLScraper
{
    public class NflService
    {
        private HttpClient _client;
        
        public NflService(HttpClient client)
        {
            _client = client;
        }
        
        // Get NFL Teams
        public async Task<TeamsModel?> GetTeamsAsync()
        {
            const string url = "https://site.api.espn.com/apis/site/v2/sports/football/nfl/teams";
            string json = await _client.GetStringAsync(url);

            return JsonConvert.DeserializeObject<TeamsModel>(json);
        }
    }
}