using Newtonsoft.Json;

namespace Sports_API.Areas.NFLScraper.Models
{
    public class NFLTeamsModel
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
        public List<MultiTeam>? Teams { get; set; }
    }

    public class MultiTeam
    {
        [JsonProperty("team")]
        public MultiTeamItem? TeamItem { get; set; }
    }
    
    public class MultiTeamItem
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