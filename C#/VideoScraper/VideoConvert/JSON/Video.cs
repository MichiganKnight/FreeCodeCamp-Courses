namespace VideoConvert.JSON
{
    public class Video
    {
        public string? Name { get; set; }
        public string? Source { get; set; }
        public string? Poster { get; set; }
        public List<string>? Tags { get; set; }
        public List<Positions>? Positions { get; set; }
    }
    
    public class LocalVideo
    {
        public string? Name { get; set; }
        public string? Source { get; set; }
        public List<string>? Tags { get; set; }
        public List<Positions>? Positions { get; set; }
    }

    public class Positions
    {
        public string? Name { get; set; }
        public string? Timestamp { get; set; }
    }
}