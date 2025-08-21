using FinanceApplication.Data;
using FinanceApplication.Models;
using Microsoft.AspNetCore.Mvc;

namespace FinanceApplication.Controllers
{
    public class ExpensesController : Controller
    {
        private readonly FinanceAppContext _context;

        public ExpensesController(FinanceAppContext context)
        {
            _context = context;
        }
        
        // GET
        public IActionResult Index()
        {
            List<Expense> expenses = _context.Expenses.ToList();
            
            return View(expenses);
        }
        
        public IActionResult Create()
        {
            return View();
        }

        [HttpPost]
        public IActionResult Create(Expense expense)
        {
            if (ModelState.IsValid)
            {
                _context.Expenses.Add(expense);
                _context.SaveChanges();
                
                return RedirectToAction("Index");
            }
            
            return View(expense);
        }
    }
}