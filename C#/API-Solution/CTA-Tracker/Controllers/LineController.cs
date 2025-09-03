using CTA_Tracker.Models;
using Microsoft.AspNetCore.Mvc;
using Newtonsoft.Json.Linq;

namespace CTA_Tracker.Controllers
{
    public class LineController(IHttpClientFactory httpClientFactory, IConfiguration config) : Controller
    {
        [HttpGet]
        public IActionResult TrainLine(string? line)
        {
            ViewBag.SelectedRoute = line;
            
            return View(new List<RouteModel>());
        }

        [HttpPost]
        [ValidateAntiForgeryToken]
        [ActionName("TrainLine")]
        public async Task<IActionResult> TrainLinePost(string route)
        {
            ViewBag.SelectedRoute = route;
            ViewBag.Requested = true;

            if (string.IsNullOrWhiteSpace(route))
            {
                ModelState.AddModelError(string.Empty, "Route Number is Required");
                
                return View(new List<RouteModel>());           
            }
            
            string? apiKey = config["API_KEY"];
            if (string.IsNullOrWhiteSpace(apiKey))
            {
                ModelState.AddModelError(string.Empty, "CTA API key is not configured.");
                return View();
            }

            string url = $"https://lapi.transitchicago.com/api/1.0/ttpositions.aspx?key={Uri.EscapeDataString(apiKey)}&rt={Uri.EscapeDataString(route)}&outputType=json";

            HttpClient client = httpClientFactory.CreateClient();
            try
            {
                using HttpResponseMessage resp = await client.GetAsync(url);
                string json = await resp.Content.ReadAsStringAsync();

                ViewBag.Requested = true;

                if (!resp.IsSuccessStatusCode)
                {
                    ModelState.AddModelError(string.Empty, $"Upstream error: {resp.StatusCode} {resp.ReasonPhrase}");
                }
                
                List<RouteModel> trains = ExtractTrainItems(json);
                
                return View(trains);
            }
            catch (HttpRequestException ex)
            {
                ViewBag.Requested = true;
                ModelState.AddModelError(string.Empty, $"Network error: {ex.Message}");
                return View();
            }
            catch (TaskCanceledException)
            {
                ViewBag.Requested = true;
                ModelState.AddModelError(string.Empty, "Request timed out.");
                return View();
            }
        }

        [HttpGet]
        public async Task<IActionResult> GetLineData(string route)
        {
            if (string.IsNullOrWhiteSpace(route))
            {
                return BadRequest("Route is Required");
            }
            
            string? apiKey = config["API_KEY"];
            if (string.IsNullOrWhiteSpace(apiKey))
            {
                return StatusCode(500, "CTA API key is not configured.");
            }

            string url = $"https://lapi.transitchicago.com/api/1.0/ttpositions.aspx?key={Uri.EscapeDataString(apiKey)}&rt={Uri.EscapeDataString(route)}&outputType=json";

            HttpClient client = httpClientFactory.CreateClient();
            HttpResponseMessage resp = await client.GetAsync(url);
            string json = await resp.Content.ReadAsStringAsync();

            if (!resp.IsSuccessStatusCode)
                return StatusCode((int)resp.StatusCode, json);
            
            return Content(json, "application/json");
        }

        private static List<RouteModel> ExtractTrainItems(string json)
        {
            List<RouteModel> result = [];

            try
            {
                JObject obj = JObject.Parse(json);

                if (obj["ctatt"] is not JObject ctaTrainTracker)
                {
                    return result;
                }

                JToken? routeToken = ctaTrainTracker?["route"];
                JArray routes = routeToken switch
                {
                    JArray arr => arr,
                    JObject singleObject => new JArray(singleObject),
                    _ => []
                };

                foreach (JToken route in routes)
                {
                    if (route is not JObject routeObj)
                    {
                        continue;
                    }

                    JToken? trainsToken = routeObj["train"];
                    JArray trains = trainsToken switch
                    {
                        JArray trainArray => trainArray,
                        JObject trainObject => new JArray(trainObject),
                        _ => []
                    };

                    foreach (JToken train in trains)
                    {
                        if (train is not JObject trainObj)
                        {
                            continue;
                        }

                        result.Add(new RouteModel
                        {
                            RouteNumber = trainObj.Value<string>("rn"),
                            Destination = trainObj.Value<string>("destNm"),
                            NextStationName = trainObj.Value<string>("nextStaNm")
                        });
                    }
                }
            }
            catch
            {
                // Ignore
            }
            
            return result;
        }
    }
}