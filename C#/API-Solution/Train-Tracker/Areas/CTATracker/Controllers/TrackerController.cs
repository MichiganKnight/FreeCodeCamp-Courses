using System.Text.Json;
using Microsoft.AspNetCore.Mvc;
using Train_Tracker.API;
using Train_Tracker.Areas.CTATracker.Models;

namespace Train_Tracker.Areas.CTATracker.Controllers
{
    [Area("CTATracker")]
    public class TrackerController(IHttpClientFactory httpClientFactory, IConfiguration config) : Controller
    {
        private static readonly string[] AllRoutes = ["red", "blue", "brn", "g", "org", "pink", "p", "y"];

        public IActionResult Index()
        {
            ViewBag.FaviconPath = "/ctaFavicon.ico";
            
            return View();
        }

        [HttpGet]
        [ResponseCache(NoStore = true, Location = ResponseCacheLocation.None)]
        public async Task<IActionResult> LivePositions([FromQuery] string[]? routes)
        {
            string[] requestedRoutes = (routes is { Length: > 0 } ? routes : AllRoutes)
                .Select(r => (r ?? string.Empty).Trim().ToLowerInvariant())
                .Distinct()
                .Where(Functions.IsValidRoute)
                .ToArray();
            
            string? apiKey = config["API_KEY"];
            if (string.IsNullOrWhiteSpace(apiKey))
            {
                return BadRequest(new { error = "Missing API key configuration." });
            }

            Task<List<RouteModel>>[] tasks = requestedRoutes.Select(rt => FetchPositionsForRoute(apiKey, rt)).ToArray();
            List<RouteModel>[] results = await Task.WhenAll(tasks);

            var trains = results
                .SelectMany(x => x)
                .Where(t => t.Latitude is not null && t.Longitude is not null && !string.IsNullOrWhiteSpace(t.RouteNumber))
                .Select(t => new
                {
                    route = t.Route,
                    routeNumber = t.RouteNumber,
                    destination = t.Destination,
                    nextStationName = t.NextStationName,
                    latitude = t.Latitude,
                    longitude = t.Longitude,
                    heading = t.Heading
                })
                .ToList();

            return new JsonResult(
                new { timestamp = DateTimeOffset.UtcNow, trains },
                new JsonSerializerOptions(JsonSerializerDefaults.Web) // camelCase
            );
        }
        
        private async Task<List<RouteModel>> FetchPositionsForRoute(string apiKey, string route)
        {
            string url = Functions.BuildRouteUrl(apiKey, route);
            HttpClient client = httpClientFactory.CreateClient();

            try
            {
                using HttpResponseMessage resp = await client.GetAsync(url);
                string json = await resp.Content.ReadAsStringAsync();

                if (!resp.IsSuccessStatusCode)
                {
                    return [];
                }

                List<RouteModel> items = Functions.ExtractRouteItems(json);

                string normalizedRoute = (route).Trim().ToLowerInvariant();

                foreach (RouteModel t in items.Where(t => string.IsNullOrWhiteSpace(t.Route)))
                {
                    t.Route = normalizedRoute;
                }

                return items;
            }
            catch
            {
                return [];
            }
        }
    }
}