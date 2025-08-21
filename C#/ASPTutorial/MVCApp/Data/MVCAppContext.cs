using Microsoft.EntityFrameworkCore;
using MVCApp.Models;

namespace MVCApp.Data
{
    public class MVCAppContext : DbContext
    {
        public MVCAppContext(DbContextOptions<MVCAppContext> options) : base(options)
        {
            
        }
        
        public DbSet<Item> Items { get; set; }
    }
}