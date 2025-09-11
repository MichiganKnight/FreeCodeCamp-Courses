using Microsoft.AspNetCore.Mvc;
using Sports_API.Areas.NFLScraper.Models;

namespace Sports_API.Areas.NFLScraper.Controllers
{
    [Area("NFLScraper")]
    public class TeamsController : Controller
    {
        [HttpGet]
        [ActionName("NFLTeams")]
        public async Task<ActionResult> NFLTeams()
        {
            using HttpClient client = new();
            NflService nfl = new(client);

            TeamsModel? teamModel = await nfl.GetTeamsAsync();
            List<MultiTeamItem> model = [];

            if (teamModel?.Sports != null)
            {
                foreach (Sport sport in teamModel.Sports)
                {
                    if (sport.Leagues == null) continue;

                    foreach (League league in sport.Leagues)
                    {
                        if (league.Teams == null) continue;

                        foreach (MultiTeam team in league.Teams)
                        {
                            MultiTeamItem? teamItem = team?.TeamItem;
                            
                            if (teamItem != null)
                            {
                                model.Add(teamItem);
                            }
                        }
                    }
                }
            }
            
            return View(model);
        }
    }
}