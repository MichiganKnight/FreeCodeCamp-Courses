using Microsoft.AspNetCore.Mvc;
using MVCApp.Models;

namespace MVCApp.Controllers
{
    public class ItemsController : Controller
    {
        public IActionResult Overview()
        {
            Item item = new Item()
            {
                Name = "Keyboard"
            };
            
            return View(item);
        }

        public IActionResult Edit(int id)
        {
            return Content("");
        }
    }
}