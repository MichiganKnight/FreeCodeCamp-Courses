using System.Net.Http.Headers;
using System.Text;
using Microsoft.AspNetCore.Mvc;
using Train_Tracker.API;
using Train_Tracker.Areas.MetraTracker.Models;

namespace Train_Tracker.Areas.MetraTracker.Controllers
{
    [Area("MetraTracker")]
    public class TripController(IHttpClientFactory httpClientFactory, IConfiguration config) : Controller
    {
        [HttpGet]
        public IActionResult RouteTrip()
        {
            ViewBag.FaviconPath = "/metraFavicon.ico";
            ViewBag.SelectedTrip = "";
            
            return View(new List<TripModel>());
        }
        
        [HttpPost]
        [ValidateAntiForgeryToken]
        [ActionName("RouteTrip")]
        public async Task<IActionResult> RouteTripPost(string trip)
        {
            ViewBag.FaviconPath = "/metraFavicon.ico";
            ViewBag.SelectedTrip = trip;
            ViewBag.Requested = true;
            
            string? metraUsername = config["METRA_USERNAME"];
            string? metraPassword = config["METRA_PASSWORD"];

            if (string.IsNullOrWhiteSpace(metraUsername) || string.IsNullOrWhiteSpace(metraPassword))
            {
                ModelState.AddModelError(string.Empty, "Invalid Credentials");
                
                return View(new List<TripModel>());
            }
            
            (bool ok, string json, string? error) = await FetchAsync(metraUsername, metraPassword);
            if (!ok)
            {
                ModelState.AddModelError(string.Empty, error ?? "Unknown Error");
                
                return View(new List<TripModel>());
            }
            
            List<TripModel> routes = Functions.ExtractMetraTrips(json);
            
            return View(routes);
        }
        
        private async Task<(bool ok, string json, string? error)> FetchAsync(string metraUsername, string metraPassword)
        {
            const string url = "https://gtfsapi.metrarail.com/gtfs/schedule/trips";
            
            string authenticationString = $"{metraUsername}:{metraPassword}";
            
            byte[] authBytes = Encoding.ASCII.GetBytes(authenticationString);
            string base64EncodedAuthString = Convert.ToBase64String(authBytes);
            
            HttpClient client = httpClientFactory.CreateClient();
            client.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Basic", base64EncodedAuthString);

            try
            {
                using HttpResponseMessage resp = await client.GetAsync(url);

                string json = await resp.Content.ReadAsStringAsync();

                return !resp.IsSuccessStatusCode
                    ? (false, json, $"Upstream Error: {resp.StatusCode} {resp.ReasonPhrase}")
                    : (true, json, null);
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