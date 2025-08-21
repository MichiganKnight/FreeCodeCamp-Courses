using FinanceApplication.Data;
using FinanceApplication.Data.Service;
using FinanceApplication.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

namespace FinanceApplication.Controllers
{
    public class ExpensesController : Controller
    {
        private readonly IExpensesService _expensesService;

        public ExpensesController(IExpensesService expensesService)
        {
            _expensesService = expensesService;
        }
        
        // GET
        public async Task<IActionResult> Index()
        {
            IEnumerable<Expense> expenses = await _expensesService.GetAllExpenses();
            
            return View(expenses);
        }
        
        public IActionResult Create()
        {
            return View();
        }

        [HttpPost]
        public async Task<IActionResult> Create(Expense expense)
        {
            if (ModelState.IsValid)
            {
                await _expensesService.AddExpense(expense);
                
                return RedirectToAction("Index");
            }
            
            return View(expense);
        }

        public IActionResult GetChart()
        {
            IQueryable data = _expensesService.GetChartData();

            return Json(data);
        }
    }
}