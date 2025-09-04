using CTA_Tracker.API;
using CTA_Tracker.Models;
using Microsoft.AspNetCore.Mvc;

namespace CTA_Tracker.Controllers
{
    public class LineController(IHttpClientFactory httpClientFactory, IConfiguration config) : Controller
    {
        private readonly IHttpClientFactory _httpClientFactory;
        private readonly IConfiguration _config = config;
        
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
            
            string? apiKey = _config["API_KEY"];
            if (string.IsNullOrWhiteSpace(apiKey))
            {
                ModelState.AddModelError(string.Empty, "Invalid API Key");
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
                    ModelState.AddModelError(string.Empty, $"Upstream Error: {resp.StatusCode} {resp.ReasonPhrase}");
                }
                
                List<RouteModel> trains = Functions.ExtractTrainItems(json);
                
                return View(trains);
            }
            catch (HttpRequestException ex)
            {
                ViewBag.Requested = true;
                ModelState.AddModelError(string.Empty, $"Network Error: {ex.Message}");
                return View();
            }
            catch (TaskCanceledException)
            {
                ViewBag.Requested = true;
                ModelState.AddModelError(string.Empty, "Request Timed Out");
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
            
            string? apiKey = _config["API_KEY"];
            if (string.IsNullOrWhiteSpace(apiKey))
            {
                return StatusCode(500, "Invalid API Key");
            }
            
            string url = $"https://lapi.transitchicago.com/api/1.0/ttpositions.aspx?key={Uri.EscapeDataString(apiKey)}&rt={Uri.EscapeDataString(route)}&outputType=json";


            HttpClient client = httpClientFactory.CreateClient();
            HttpResponseMessage resp = await client.GetAsync(url);
            string json = await resp.Content.ReadAsStringAsync();

            if (!resp.IsSuccessStatusCode)
            {
                return StatusCode((int)resp.StatusCode, json);
            }
            
            return Content(json, "application/json");
        }
    }
}