using Microsoft.AspNetCore.Mvc;

namespace VideoServer.Controllers
{
    public class NSFWVideosController : Controller
    {
        // GET
        public IActionResult TNA()
        {
            return View();
        }

        public IActionResult SpankBang()
        {
            return View();
        }

        public IActionResult Local()
        {
            return View();
        }
    }
}