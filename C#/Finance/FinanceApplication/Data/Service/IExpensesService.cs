using FinanceApplication.Models;

namespace FinanceApplication.Data.Service
{
    public interface IExpensesService
    {
        Task<IEnumerable<Expense>> GetAllExpenses();
        Task AddExpense(Expense expense);
    }
}