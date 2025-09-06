using System.Net.Http.Headers;
using System.Text;
using Microsoft.AspNetCore.Mvc;
using Train_Tracker.API;
using Train_Tracker.Areas.MetraTracker.Models;

namespace Train_Tracker.Areas.MetraTracker.Controllers
{
    [Area("MetraTracker")]
    public class TrackerController(IHttpClientFactory httpClientFactory, IConfiguration config) : Controller
    {
        [HttpGet]
        public IActionResult Index()
        {
            ViewBag.FaviconPath = "/metraFavicon.ico";
            ViewBag.SelectedRoute = "";
            
            return View(new List<RouteModel>());
        }

        [HttpPost]
        [ValidateAntiForgeryToken]
        [ActionName("Index")]
        public async Task<IActionResult> IndexPost()
        {
            ViewBag.FaviconPath = "/metraFavicon.ico";
            ViewBag.SelectedRoute = "";
            ViewBag.Requested = true;
            
            string? metraUsername = config["METRA_USERNAME"];
            string? metraPassword = config["METRA_PASSWORD"];

            if (string.IsNullOrWhiteSpace(metraUsername) || string.IsNullOrWhiteSpace(metraPassword))
            {
                ModelState.AddModelError(string.Empty, "Invalid Credentials");
                
                return View(new List<RouteModel>());
            }
            
            (bool ok, string json, string? error) = await FetchAsync(metraUsername, metraPassword);
            if (!ok)
            {
                ModelState.AddModelError(string.Empty, error ?? "Unknown Error");
                
                return View(new List<RouteModel>());
            }
            
            List<RouteModel> routes = Functions.ExtractMetraRoutes(json);
            
            return View(routes);
        }

        private async Task<(bool ok, string json, string? error)> FetchAsync(string metraUsername, string metraPassword)
        {
            const string url = "https://gtfsapi.metrarail.com/gtfs/schedule/routes";
            
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