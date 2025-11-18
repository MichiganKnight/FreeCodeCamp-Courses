using Microsoft.AspNetCore.Mvc;
using Newtonsoft.Json;
using VideoConvert.JSON;
using VideoServer.Models;

namespace VideoServer.Controllers
{
    public class VideosController : Controller
    {
        private static readonly string[] DataSources =
        [
            Path.Combine("wwwroot", "Data", "Video.json")
        ];
        
        private static readonly string VideoFile = Path.Combine("wwwroot", "Data", "Video.json");
        
        // GET
        public IActionResult Local()
        {
            VideoViewModel model = new();
            
            if (System.IO.File.Exists(VideoFile))
            {
                string json = System.IO.File.ReadAllText(VideoFile);
                model.Videos = JsonConvert.DeserializeObject<List<Video>>(json);
            };
            
            return View(model);
        }
    }
}