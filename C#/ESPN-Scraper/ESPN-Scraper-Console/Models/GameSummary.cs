using Newtonsoft.Json;

namespace ESPN_Scraper_Console.Models
{
    /// <summary>
    /// Represents the Game Summary - Includes Box Score and Scoring Plays
    /// </summary>
    public class GameSummary
    {
        [JsonProperty("boxscore")]
        public BoxScore? BoxScore { get; set; }
        
        [JsonProperty("scoringPlays")]
        public List<ScoringPlay>? ScoringPlays { get; set; }
    }
    
    /// <summary>
    /// Represents the Box Score - Includes Teams and Players
    /// </summary>
    public class BoxScore
    {
        [JsonProperty("teams")]
        public List<TeamStats>? Teams { get; set; }
        
        [JsonProperty("players")]
        public List<PlayerStats>? Players { get; set; }
    }

    /// <summary>
    /// Represents Team Stats - Includes Team and Stats
    /// </summary>
    public class TeamStats
    {
        [JsonProperty("team")]
        public Team? Team { get; set; }
        
        [JsonProperty("statistics")]
        public List<StatCategory>? Statistics { get; set; }
    }
    
    public class PlayerStats
    {
        [JsonProperty("team")]
        public Team? Team { get; set; }
        
        [JsonProperty("statistics")]
        public List<StatCategory>? Statistics { get; set; }
    }
    
    /// <summary>
    /// Represents a Stat Category - Includes Name, DisplayName, Labels, and Athletes
    /// </summary>
    public class StatCategory
    {
        [JsonProperty("name")]
        public string? Name { get; set; }
        
        //[JsonProperty("displayName")]
        //public string? DisplayName { get; set; }
        
        [JsonProperty("labels")]
        public List<string>? Labels { get; set; }
        
        [JsonProperty("athletes")]
        public List<AthleteStats>? Athletes { get; set; }
    }
    
    /// <summary>
    /// Represents Athlete Stats - Includes Athlete, Stats
    /// </summary>
    public class AthleteStats
    {
        [JsonProperty("athlete")]
        public Athlete? Athlete { get; set; }
        
        [JsonProperty("stats")]
        public List<string>? Stats { get; set; }
    }
    
    /// <summary>
    /// Represents an Athlete - Includes ID and Display Name
    /// </summary>
    public class Athlete
    {
        [JsonProperty("id")]
        public string? Id { get; set; }
        
        [JsonProperty("displayName")]
        public string? DisplayName { get; set; }
    }

    /// <summary>
    /// Represents a Scoring Play - Includes Text, Period, and Clock
    /// </summary>
    public class ScoringPlay
    {
        [JsonProperty("text")]
        public string? Text { get; set; }
        
        [JsonProperty("period")]
        public Period? Period { get; set; }
        
        [JsonProperty("clock")]
        public Clock? Clock { get; set; }
    }
    
    public class Period
    {
        [JsonProperty("number")]
        public int Number { get; set; }
    }
    
    public class Clock
    {
        [JsonProperty("displayValue")]
        public string? DisplayValue { get; set; }
    }
}