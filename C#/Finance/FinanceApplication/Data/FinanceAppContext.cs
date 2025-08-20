using FinanceApplication.Models;
using Microsoft.EntityFrameworkCore;

namespace FinanceApplication.Data
{
    public class FinanceAppContext : DbContext
    {
        public FinanceAppContext(DbContextOptions<FinanceAppContext> options) : base(options)
        {
            
        }
        
        DbSet<Expense> Expenses { get; set; }
    }
}