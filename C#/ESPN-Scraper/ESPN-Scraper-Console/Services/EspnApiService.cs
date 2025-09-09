using ESPN_Scraper_Console.Models;
using Newtonsoft.Json;

namespace ESPN_Scraper_Console.Services
{
    public class EspnApiService
    {
        private readonly HttpClient _client;

        public EspnApiService(HttpClient client)
        {
            _client = client;
        }
        
        // NFL Scoreboard
        public async Task<Scoreboard?> GetScoreboardAsync()
        {
            string url = "https://site.api.espn.com/apis/site/v2/sports/football/nfl/scoreboard";
            string json = await _client.GetStringAsync(url);

            return JsonConvert.DeserializeObject<Scoreboard>(json);
        }
        
        // Game Summary
        public async Task<GameSummary?> GetGameSummaryAsync(string gameId)
        {
            string url = $"https://site.api.espn.com/apis/site/v2/sports/football/nfl/summary?event={gameId}";
            string json = await _client.GetStringAsync(url);
            
            return JsonConvert.DeserializeObject<GameSummary>(json);
        }
        
        // Player Stats
        public async Task<PlayerSeasonStats?> GetPlayerStatsAsync(string playerId)
        {
            string url = $"https://site.web.api.espn.com/apis/common/v3/sports/football/nfl/athletes/{playerId}/stats";
            string json = await _client.GetStringAsync(url);
            
            return JsonConvert.DeserializeObject<PlayerSeasonStats>(json);
        }
        
        // Player Game Log
        public async Task<string> GetPlayerGameLogAsync(string playerId)
        {
            string url = $"https://site.web.api.espn.com/apis/common/v3/sports/football/nfl/athletes/{playerId}/gamelog";
            return await _client.GetStringAsync(url);
            
            // TODO: Build Typed Model if You Want Strongly Typed Game Logs
        }
    }
}