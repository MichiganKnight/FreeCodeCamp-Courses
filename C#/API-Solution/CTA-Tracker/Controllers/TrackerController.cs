using Microsoft.AspNetCore.Mvc;

namespace CTA_Tracker.Controllers
{
    public class TrackerController : Controller
    {
        public IActionResult Index()
        {
            return View();
        }
    }
}