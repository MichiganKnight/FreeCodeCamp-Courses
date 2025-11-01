using System.Globalization;
using Microsoft.AspNetCore.Mvc;
using Newtonsoft.Json;
using VideoConvert.JSON;
using VideoServer.Models;

namespace VideoServer.Controllers
{
    public class NSFWVideosController : Controller
    {
        // GET
        public IActionResult TNA()
        {
            return View();
        }

        public IActionResult SpankBang()
        {
            return View();
        }

        public IActionResult Local()
        {
            return View();
        }
        
        public IActionResult Categories()
        {
            CategoriesViewModel model = new();

            string[] sources =
            {
                Path.Combine("wwwroot", "Data", "TNA.json"),
                Path.Combine("wwwroot", "Data", "SpankBang.json"),
                Path.Combine("wwwroot", "Data", "Local.json")
            };

            HashSet<string> tagSet = new(StringComparer.OrdinalIgnoreCase);
            Dictionary<string, string> tagMap = new(StringComparer.OrdinalIgnoreCase)
            {
                ["dp"] = "DP",
                ["wtfpass"] = "WTFPass",
                ["3some"] = "Threesome",
                ["groupsex"] = "Group Sex",
                ["Pick Up"] = "Pickup"
            };

            foreach (string file in sources)
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

                        string formatted = tagMap.ContainsKey(tag.Trim().ToLower())
                            ? tagMap[tag.Trim().ToLower()]
                            : CultureInfo.CurrentCulture.TextInfo.ToTitleCase(tag.Trim().ToLower());

                        tagSet.Add(formatted);
                    }
                }
            }

            model.AllTags = tagSet.OrderBy(t => t, StringComparer.OrdinalIgnoreCase).ToList();

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

            string[] sources =
            [
                Path.Combine("wwwroot", "Data", "TNA.json"),
                Path.Combine("wwwroot", "Data", "SpankBang.json"),
                Path.Combine("wwwroot", "Data", "Local.json")
            ];
            
            Dictionary<string, string> tagMap = new(StringComparer.OrdinalIgnoreCase)
            {
                ["dp"] = "DP",
                ["wtfpass"] = "WTFPass",
                ["3some"] = "Threesome",
                ["groupsex"] = "Group Sex",
                ["Pick Up"] = "Pickup"
            };
            
            foreach (string file in sources)
            {
                if (!System.IO.File.Exists(file)) continue;

                string json = System.IO.File.ReadAllText(file);
                List<Video>? videos = JsonConvert.DeserializeObject<List<Video>>(json);

                if (videos == null) continue;

                foreach (Video video in videos)
                {
                    // Normalize tags for matching
                    var normalizedTags = (video.Tags ?? [])
                        .Select(t => tagMap.ContainsKey(t.Trim().ToLower()) ? tagMap[t.Trim().ToLower()] : CultureInfo.CurrentCulture.TextInfo.ToTitleCase(t.Trim().ToLower()))
                        .ToList();

                    if (normalizedTags.Contains(category, StringComparer.OrdinalIgnoreCase))
                    {
                        model.Videos.Add(video);
                    }
                }
            }

            return View(model);
        }
    }
}