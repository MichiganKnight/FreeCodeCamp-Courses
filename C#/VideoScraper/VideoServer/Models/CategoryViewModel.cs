using VideoConvert.JSON;

namespace VideoServer.Models
{
    public class CategoryViewModel
    {
        public string CategoryName { get; set; } = string.Empty;
        public List<Video> Videos { get; set; } = [];
    }
}