using FinanceApplication.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

namespace FinanceApplication.Data.Service
{
    public class ExpensesService : IExpensesService
    {
        private readonly FinanceAppContext _context;

        public ExpensesService(FinanceAppContext context)
        {
            _context = context;
        }

        public async Task AddExpense(Expense expense)
        {
            _context.Expenses.Add(expense);

            await _context.SaveChangesAsync();
        }

        public async Task<IEnumerable<Expense>> GetAllExpenses()
        {
            List<Expense> expenses = await _context.Expenses.ToListAsync();

            return expenses;
        }

        public IQueryable GetChartData()
        {
            IQueryable data = _context.Expenses.GroupBy(e => e.Category)
                .Select(g => new
                {
                    Category = g.Key, Total = g.Sum(e => e.Amount)
                });

            return data;
        }
    }
}