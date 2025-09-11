using Newtonsoft.Json;

namespace Sports_API.Areas.CFBScraper.Models
{
    public class CFBTeamModel
    {
        [JsonProperty("team")]
        public Team? Team { get; set; }
    }

    public class Team
    {
        [JsonProperty("id")]
        public int Id { get; set; }
        
        [JsonProperty("color")]
        public string? Color { get; set; }
        
        [JsonProperty("record")]
        public Record? Record { get; set; }
    }

    public class Record
    {
        [JsonProperty("items")]
        public List<Item>? Items { get; set; }
    }

    public class Item
    {
        [JsonProperty("summary")]
        public string? Summary { get; set; }
        
        [JsonProperty("stats")]
        public List<TeamStats>? TeamStats { get; set; }
    }

    public class TeamStats
    {
        [JsonProperty("name")]
        public string? Name { get; set; }
        
        [JsonProperty("value")]
        public string? Value { get; set; }
    }
}