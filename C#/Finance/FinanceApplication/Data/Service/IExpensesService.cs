using FinanceApplication.Models;

namespace FinanceApplication.Data.Service
{
    public interface IExpensesService
    {
        Task AddExpense(Expense expense);
        Task<IEnumerable<Expense>> GetAllExpenses();

        IQueryable GetChartData();
    }
}