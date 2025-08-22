using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using MVCApp.Data;
using MVCApp.Models;

namespace MVCApp.Controllers
{
    public class ItemsController : Controller
    {
        private readonly MVCAppContext _context;

        public ItemsController(MVCAppContext context)
        {
            _context = context;
        }
        
        public async Task<IActionResult> Index()
        {
            List <Item> items = await _context.Items.ToListAsync();
            
            return View(items);
        }

        public IActionResult Create()
        {
            return View();
        }

        [HttpPost]
        public async Task<IActionResult> Create([Bind("Id, Name, Price")] Item item)
        {
            if (ModelState.IsValid)
            {
                _context.Items.Add(item);
                await _context.SaveChangesAsync();

                return RedirectToAction("Index");
            }
            
            return View(item);
        }

        public async Task<IActionResult> Edit(int id)
        {
            Item? item = await _context.Items.FirstOrDefaultAsync(i => i.Id == id);
            
            return View(item);
        }
        
        [HttpPost]
        public async Task<IActionResult> Edit(int id, [Bind("Id, Name, Price")] Item item)
        {
            if (ModelState.IsValid)
            {
                _context.Items.Update(item);
                await _context.SaveChangesAsync();
                
                return RedirectToAction("Index");
            }
            
            return View(item);
        }

        public async Task<IActionResult> Delete(int id)
        {
            Item? item = await _context.Items.FirstOrDefaultAsync(i => i.Id == id);
            
            return View(item);
        }

        [HttpPost, ActionName("Delete")]
        public async Task<IActionResult> DeleteConfirmed(int id)
        {
            Item? item = await _context.Items.FindAsync(id);

            if (item != null)
            {
                _context.Items.Remove(item);
                await _context.SaveChangesAsync();
            }
            
            return RedirectToAction("Index");
        }
    }
}