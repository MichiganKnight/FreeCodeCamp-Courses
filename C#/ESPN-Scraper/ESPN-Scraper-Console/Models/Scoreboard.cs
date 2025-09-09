using Newtonsoft.Json;

namespace ESPN_Scraper_Console.Models
{
    /// <summary>
    /// Represents the Scoreboard - Includes Events
    /// </summary>
    public class Scoreboard
    {
        [JsonProperty("events")]
        public List<Event>? Events { get; set; }
    }

    /// <summary>
    /// Represents an Event - Includes Competitions
    /// </summary>
    public class Event
    {
        [JsonProperty("id")]
        public string? Id { get; set; }
        
        [JsonProperty("name")]
        public string? Name { get; set; }
        
        [JsonProperty("date")]
        public string? Date { get; set; }
        
        [JsonProperty("competitions")]
        public List<Competition>? Competitions { get; set; }
    }

    /// <summary>
    /// Represents a Competition - Includes Competitors
    /// </summary>
    public class Competition
    {
        [JsonProperty("competitors")]
        public List<Competitor>? Competitors { get; set; }
    }

    /// <summary>
    /// Represents a Competitor - Includes Team and Score
    /// </summary>
    public class Competitor
    {
        [JsonProperty("team")]
        public Team? Team { get; set; }
        
        [JsonProperty("score")]
        public string? Score { get; set; }
    }

    /// <summary>
    /// Represents a Sports Team - Includes Display Name
    /// </summary>
    public class Team
    {
        [JsonProperty("displayName")]
        public string? DisplayName { get; set; }
    }
}