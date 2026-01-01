using System.Diagnostics;
using ComputerUpgrade.Classes;
using ComputerUpgrade.Models;
using Microsoft.AspNetCore.Mvc;

namespace ComputerUpgrade.Controllers
{
    public class HomeController : Controller
    {
        public IActionResult Index()
        {
            return View();
        }

        public IActionResult MicrocenterComponents()
        {
            return View();
        }

        public ActionResult CheckAvailability()
        {
            foreach (string url in Component.MicrocenterUrlMap.Select(item => item.Value))
            {
                bool available = Helpers.CheckAvailability(url);
            }

            return null;
        }

        [ResponseCache(Duration = 0, Location = ResponseCacheLocation.None, NoStore = true)]
        public IActionResult Error()
        {
            return View(new ErrorViewModel { RequestId = Activity.Current?.Id ?? HttpContext.TraceIdentifier });
        }
    }
}