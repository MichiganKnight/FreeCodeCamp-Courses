using Microsoft.AspNetCore.Mvc;

namespace Sports_API.Areas.NFLScraper.Controllers
{
    [Area("NFLScraper")]
    public class TeamController : Controller
    {
        public IActionResult NFLTeam(string? team)
        {
            ViewBag.TeamName = team;
            
            return View();
        }
    }
}