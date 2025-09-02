using Microsoft.AspNetCore.Mvc;

namespace CTA_Tracker.Controllers
{
    public class RouteController : Controller
    {
        [HttpGet]
        public IActionResult RouteNumber(string rn)
        {
            if (string.IsNullOrWhiteSpace(rn))
            {
                return RedirectToAction("Index", "Home");
            }
            
            ViewBag.RouteNumber = rn;
            
            return View();
        }
    }
}