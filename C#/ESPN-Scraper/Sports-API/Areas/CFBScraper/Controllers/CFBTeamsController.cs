using Microsoft.AspNetCore.Mvc;
using Sports_API.Areas.CFBScraper.Models;

namespace Sports_API.Areas.CFBScraper.Controllers
{
    [Area("CFBScraper")]
    public class CFBTeamsController : Controller
    {
        [HttpGet]
        [ActionName("Teams")]
        public async Task<ActionResult> Teams()
        {
            using HttpClient client = new();
            CfbService cfb = new(client);

            CFBTeamsModel? teamModel = await cfb.GetTeamsAsync();
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