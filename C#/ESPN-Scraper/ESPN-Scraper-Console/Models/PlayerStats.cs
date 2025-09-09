using Newtonsoft.Json;

namespace ESPN_Scraper_Console.Models
{
    public class PlayerSeasonStats
    {
        [JsonProperty("athlete")]
        public Athlete? Athlete { get; set; }
        
        [JsonProperty("splits")]
        public List<Split>? Splits { get; set; }
    }

    public class Split
    {
        [JsonProperty("categories")]
        public List<Category>? Categories { get; set; }
    }
    
    public class Category
    {
        [JsonProperty("name")]
        public string? Name { get; set; }
        
        [JsonProperty("displayName")]
        public string? DisplayName { get; set; }
        
        [JsonProperty("stats")]
        public List<PlayerStat>? Stats { get; set; }
    }
    
    public class PlayerStat
    {
        [JsonProperty("label")]
        public string? Label { get; set; }
        
        [JsonProperty("value")]
        public string? Value { get; set; }
    }
}