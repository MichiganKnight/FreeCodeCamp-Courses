using System.Diagnostics;
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

        public IActionResult CorsairComponents()
        {
            return View();
        }

        public IActionResult AllComponents()
        {
            return View();
        }

        [ResponseCache(Duration = 0, Location = ResponseCacheLocation.None, NoStore = true)]
        public IActionResult Error()
        {
            return View(new ErrorViewModel { RequestId = Activity.Current?.Id ?? HttpContext.TraceIdentifier });
        }
    }
}