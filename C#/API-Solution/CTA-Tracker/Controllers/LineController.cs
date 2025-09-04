using CTA_Tracker.API;
using CTA_Tracker.Models;
using Microsoft.AspNetCore.Mvc;

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

            if (!Functions.IsValidRoute(route))
            {
                ModelState.AddModelError(string.Empty, "Invalid Route");
                
                return View(new List<RouteModel>());           
            }
            
            string? apiKey = config["API_KEY"];
            if (string.IsNullOrWhiteSpace(apiKey))
            {
                ModelState.AddModelError(string.Empty, "Invalid API Key");
                
                return View(new List<RouteModel>());
            }
            
            (bool ok, string json, string? error) = await FetchAsync(apiKey, route);
            if (!ok)
            {
                ModelState.AddModelError(string.Empty, error ?? "Unknown Error");
                
                return View(new List<RouteModel>());
            }
            
            List<RouteModel> trains = Functions.ExtractTrainItems(json);
            ViewBag.Timestamp = Functions.TryGetTimestamp(json);
            
            return View(trains);
        }

        [HttpGet]
        public async Task<IActionResult> GetLineData(string route)
        {
            if (!Functions.IsValidRoute(route))
            {
                return BadRequest("Route is Required & Must be a Valid CTA Route");
            }
            
            string? apiKey = config["API_KEY"];
            if (string.IsNullOrWhiteSpace(apiKey))
            {
                return StatusCode(500, "Invalid API Key");
            }
            
            (bool ok, string json, string? error) = await FetchAsync(apiKey, route);
            if (!ok)
            {
                return StatusCode(500, error ?? "Upstream Error");
            }
            
            return Content(json, "application/json");
        }

        private async Task<(bool ok, string json, string? error)> FetchAsync(string apiKey, string route)
        {
            string url = Functions.BuildCtaUrl(apiKey, route);
            
            HttpClient client = httpClientFactory.CreateClient();

            try
            {
                using HttpResponseMessage resp = await client.GetAsync(url);

                string json = await resp.Content.ReadAsStringAsync();

                return !resp.IsSuccessStatusCode ? (false, json, $"Upstream Error: {resp.StatusCode} {resp.ReasonPhrase}") : (true, json, null);
            }
            catch (HttpRequestException ex)
            {
                return (false, string.Empty, $"Network Error: {ex.Message}");
            }
            catch (TaskCanceledException)
            {
                return (false, string.Empty, "Request Timeout");
            }
        }
    }
}