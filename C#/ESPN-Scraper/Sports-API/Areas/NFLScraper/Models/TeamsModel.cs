using Newtonsoft.Json;

namespace Sports_API.Areas.NFLScraper.Models
{
    public class TeamsModel
    {
        [JsonProperty("sports")]
        public List<Sport>? Sports { get; set; }
    }

    public class Sport
    {
        [JsonProperty("id")]
        public int Id { get; set; }
        
        [JsonProperty("name")]
        public string? Name { get; set; }
        
        [JsonProperty("leagues")]
        public List<League>? Leagues { get; set; }
    }

    public class League
    {
        [JsonProperty("teams")]
        public List<Team>? Teams { get; set; }
    }

    public class Team
    {
        [JsonProperty("team")]
        public TeamItem? TeamItem { get; set; }
    }
    
    public class TeamItem
    {
        [JsonProperty("id")]
        public int Id { get; set; }
        
        [JsonProperty("abbreviation")]
        public string? Abbreviation { get; set; }
        
        [JsonProperty("displayName")]
        public string? DisplayName { get; set; }
        
        [JsonProperty("color")]
        public string? Color { get; set; }
    }
}