using VideoConvert.JSON;

namespace VideoServer.Models
{
    public class VideoViewModel
    {
        public List<Video>? Videos { get; set; } = [];
        public List<string>? Tags { get; set; } = [];
    }
}