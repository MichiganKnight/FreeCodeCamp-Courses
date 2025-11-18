using System.Globalization;
using Microsoft.AspNetCore.Mvc;
using Newtonsoft.Json;
using VideoConvert.JSON;
using VideoServer.Models;

namespace VideoServer.Controllers
{
    public class NSFWVideosController : Controller
    {
        private static readonly string[] DataSources =
        [
            Path.Combine("wwwroot", "Data", "TNA.json"),
            Path.Combine("wwwroot", "Data", "SpankBang.json"),
            Path.Combine("wwwroot", "Data", "LocalNSFW.json")
        ];
        
        private static readonly string TNAFile = Path.Combine("wwwroot", "Data", "TNA.json");
        private static readonly string SpankBangFile = Path.Combine("wwwroot", "Data", "SpankBang.json");
        private static readonly string LocalFile = Path.Combine("wwwroot", "Data", "LocalNSFW.json");
        
        private static readonly HashSet<string> TagSet = new(StringComparer.OrdinalIgnoreCase);
        private static readonly Dictionary<string, string> TagMap = new(StringComparer.OrdinalIgnoreCase)
        {
            ["dp"] = "DP",
            ["wtfpass"] = "WTFPass",
            ["3some"] = "Threesome",
            ["4Some"] = "Foursome",
            ["Group"] = "Group Sex",
            ["groupsex"] = "Group Sex",
            ["Pick Up"] = "Pickup",
            ["Ffm"] = "FFM",
            ["Outdoor"] = "Outdoors",
            ["New Face"] = "New",
            ["New Teen"] = "New",
            ["Rough Sex"] = "Rough",
            ["Doggy Style"] = "Doggystyle",
            ["Porno"] = "Porn",
            ["Blowjobs"] = "Blowjob"            
        };
        
        // GET
        public IActionResult TNA()
        {
            VideoViewModel model = new();

            if (System.IO.File.Exists(TNAFile))
            {
                string json = System.IO.File.ReadAllText(TNAFile);
                model.Videos = JsonConvert.DeserializeObject<List<Video>>(json);
            };
            
            return View(model);
        }

        public IActionResult SpankBang()
        {
            VideoViewModel model = new();
            
            if (System.IO.File.Exists(SpankBangFile))
            {
                string json = System.IO.File.ReadAllText(SpankBangFile);
                model.Videos = JsonConvert.DeserializeObject<List<Video>>(json);
            };
            
            return View(model);
        }

        public IActionResult Local()
        {
            VideoViewModel model = new();
            
            if (System.IO.File.Exists(LocalFile))
            {
                string json = System.IO.File.ReadAllText(LocalFile);
                model.Videos = JsonConvert.DeserializeObject<List<Video>>(json);
            };
            
            return View(model);
        }
        
        public IActionResult Categories()
        {
            CategoriesViewModel model = new();

            foreach (string file in DataSources)
            {
                if (!System.IO.File.Exists(file)) continue;

                string json = System.IO.File.ReadAllText(file);
                List<Video>? videos = JsonConvert.DeserializeObject<List<Video>>(json);

                if (videos == null) continue;

                foreach (Video video in videos)
                {
                    foreach (string tag in video.Tags ?? [])
                    {
                        if (string.IsNullOrWhiteSpace(tag)) continue;

                        string formatted = TagMap.ContainsKey(tag.Trim().ToLower())
                            ? TagMap[tag.Trim().ToLower()]
                            : CultureInfo.CurrentCulture.TextInfo.ToTitleCase(tag.Trim().ToLower());

                        TagSet.Add(formatted);
                    }
                }
            }

            model.AllTags = TagSet.OrderBy(t => t, StringComparer.OrdinalIgnoreCase).ToList();

            return View(model);
        }
        
        public IActionResult Category(string category)
        {
            if (string.IsNullOrWhiteSpace(category))
            {
                return RedirectToAction("Categories");
            }

            CategoryViewModel model = new()
            {
                CategoryName = category
            };
            
            foreach (string file in DataSources)
            {
                if (!System.IO.File.Exists(file)) continue;

                string json = System.IO.File.ReadAllText(file);
                List<Video>? videos = JsonConvert.DeserializeObject<List<Video>>(json);

                if (videos == null) continue;

                foreach (Video video in videos)
                {
                    List<string> normalizedTags = (video.Tags ?? [])
                        .Select(t => TagMap.ContainsKey(t.Trim().ToLower())
                            ? TagMap[t.Trim().ToLower()]
                            : CultureInfo.CurrentCulture.TextInfo.ToTitleCase(t.Trim().ToLower()))
                        .ToList();

                    if (normalizedTags.Contains(category, StringComparer.OrdinalIgnoreCase))
                    {
                        Video displayVideo = new()
                        {
                            Name = video.Name,
                            Source = video.Source,
                            Poster = video.Poster,
                            Tags = normalizedTags
                        };
                        
                        model.Videos.Add(displayVideo);
                    }
                }
            }

            return View(model);
        }
    }
}