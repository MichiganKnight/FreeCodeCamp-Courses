using CTA_Tracker.API;
using CTA_Tracker.Models;
using Microsoft.AspNetCore.Mvc;

namespace CTA_Tracker.Controllers
{
    public class RouteController(IHttpClientFactory httpClientFactory, IConfiguration config) : Controller
    {
        [HttpGet]
        public IActionResult RouteNumber(string? rn, string? route)
        {
            ViewBag.RouteNumber = rn;
            ViewBag.SelectedRoute = route;
            
            return View(new List<TrainModel>());
        }
        
        [HttpPost]
        [ValidateAntiForgeryToken]
        [ActionName("RouteNumber")]
        public async Task<IActionResult> RouteNumberPost(string? rn, string? route)
        {
            ViewBag.RouteNumber = rn;
            ViewBag.SelectedRoute = route;
            ViewBag.Requested = true;

            if (!Functions.IsValidRunNumber(rn))
            {
                ModelState.AddModelError(string.Empty, "Invalid Run Number");
                
                return View(new List<TrainModel>());
            }
            
            string? apiKey = config["API_KEY"];
            if (string.IsNullOrWhiteSpace(apiKey))
            {
                ModelState.AddModelError(string.Empty, "Invalid API Key");
                
                return View(new List<TrainModel>());
            }
            
            (bool ok, string json, string? error) = await FetchAsync(apiKey, rn!);
            if (!ok)
            {
                ModelState.AddModelError(string.Empty, error ?? "Unknown Error");
                
                return View(new List<TrainModel>());
            }
            
            List<TrainModel> trains = Functions.ExtractTrainItems(json);
            ViewBag.Timestamp = Functions.TryGetTimestamp(json);
            
            return View(trains);
        }

        private async Task<(bool ok, string json, string? error)> FetchAsync(string apiKey, string rn)
        {
            string url = Functions.BuildTrainUrl(apiKey, rn);
            
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