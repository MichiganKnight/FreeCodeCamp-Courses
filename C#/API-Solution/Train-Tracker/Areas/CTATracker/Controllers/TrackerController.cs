using Microsoft.AspNetCore.Mvc;

namespace Train_Tracker.Areas.CTATracker.Controllers
{
    [Area("CTATracker")]
    public class TrackerController : Controller
    {
        public IActionResult Index()
        {
            return View();
        }
    }
}