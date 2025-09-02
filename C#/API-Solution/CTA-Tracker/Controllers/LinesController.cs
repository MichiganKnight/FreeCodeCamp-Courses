using System.Text;
using CTA_Tracker.Models;
using Microsoft.AspNetCore.Mvc;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;

namespace CTA_Tracker.Controllers
{
    public class LinesController : Controller
    {
        private readonly IHttpClientFactory _httpClientFactory;
        private readonly IConfiguration _config;

        public LinesController(IHttpClientFactory httpClientFactory, IConfiguration config)
        {
            _httpClientFactory = httpClientFactory;
            _config = config;
        }

        [HttpGet]
        public IActionResult RedLine()
        {
            return View();
        }

        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> RedLine(string rt = "red")
        {
            string? apiKey = _config["API_KEY"];
            if (string.IsNullOrWhiteSpace(apiKey))
            {
                ModelState.AddModelError(string.Empty, "CTA API key is not configured.");
                return View();
            }

            string url = $"https://lapi.transitchicago.com/api/1.0/ttpositions.aspx?key={Uri.EscapeDataString(apiKey)}&rt={Uri.EscapeDataString(rt)}&outputType=json";

            HttpClient client = _httpClientFactory.CreateClient();
            try
            {
                using HttpResponseMessage resp = await client.GetAsync(url);
                string json = await resp.Content.ReadAsStringAsync();

                ViewBag.Requested = true;

                if (!resp.IsSuccessStatusCode)
                {
                    ModelState.AddModelError(string.Empty, $"Upstream error: {resp.StatusCode} {resp.ReasonPhrase}");
                }

                ViewBag.Json = PrettyJson(json);
                
                List<TrainItem> trains = ExtractTrainItems(json);
                
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
        public async Task<IActionResult> GetRedLineData(string rt = "red")
        {
            string? apiKey = _config["API_KEY"];
            if (string.IsNullOrWhiteSpace(apiKey))
                return StatusCode(500, "CTA API key is not configured.");

            string url = $"https://lapi.transitchicago.com/api/1.0/ttpositions.aspx?key={Uri.EscapeDataString(apiKey)}&rt={Uri.EscapeDataString(rt)}&outputType=json";

            HttpClient client = _httpClientFactory.CreateClient();
            HttpResponseMessage resp = await client.GetAsync(url);
            string json = await resp.Content.ReadAsStringAsync();

            if (!resp.IsSuccessStatusCode)
                return StatusCode((int)resp.StatusCode, json);
            
            return Content(json, "application/json");
        }

        private static string PrettyJson(string json)
        {
            try
            {
                JObject obj = JObject.Parse(json);
                JObject? ctaTrainTracker = obj["ctatt"] as JObject;

                JArray routes;
                {
                    JToken? routeToken = ctaTrainTracker?["route"];
                    routes = routeToken switch
                    {
                        JArray arr => arr,
                        JObject singleObject => new JArray(singleObject),
                        _ => []
                    };
                }
                
                if (ctaTrainTracker is null)
                {
                    return obj.ToString(Formatting.Indented);
                }

                string? timestamp = ctaTrainTracker.Value<string>("tmst");
                
                StringBuilder sb = new();
                sb.AppendLine("CTA Train Tracker:");
                sb.AppendLine($"Timestamp: {timestamp}");
                
                if (routes?.Count > 0)
                {
                    JObject firstRoute = (JObject)routes[0];
                    string? routeName = firstRoute.Value<string>("@name");
                    sb.AppendLine($"Route Name: {CapitalizeFistLetter(routeName)} Line");
                    
                    JToken? trainsToken = firstRoute["train"];
                    JArray trains = trainsToken switch
                    {
                        JArray trainArray => trainArray,
                        JObject trainObject => new JArray(trainObject),
                        _ => []
                    };

                    sb.AppendLine($"Trains: {trains.Count}");

                    for (int i = 0; i < trains.Count; i++)
                    {
                        JObject train = (JObject)trains[i];

                        sb.AppendLine($"Train {i + 1} - Route Number: {train.Value<string>("rn")}");
                        sb.AppendLine($"Train {i + 1} - Destination: {train.Value<string>("destNm")}");
                        sb.AppendLine($"Train {i + 1} - Next Station Name: {train.Value<string>("nextStaNm")}");
                    }

                    sb.AppendLine("");
                }
                else
                {
                    sb.AppendLine("Routes Array is Empty\n");
                }
                
                foreach (JProperty prop in ctaTrainTracker.Properties())
                {
                    sb.AppendLine($" {prop.Name}: {prop.Value}");
                }
                
                return sb.ToString();
            }
            catch
            {
                return json;
            }
        }

        private static string CapitalizeFistLetter(string? input)
        {
            if (string.IsNullOrEmpty(input))
            {
                return string.Empty;
            }
            
            return char.ToUpper(input[0]) + input[1..];
        }

        private static List<TrainItem> ExtractTrainItems(string json)
        {
            List<TrainItem> result = [];

            try
            {
                JObject obj = JObject.Parse(json);
                JObject? ctaTrainTracker = obj["ctatt"] as JObject;

                if (ctaTrainTracker is null)
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
                    JObject? routeObj = route as JObject;

                    if (routeObj is null)
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

                        result.Add(new TrainItem
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