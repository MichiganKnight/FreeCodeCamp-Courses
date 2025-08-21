using System.ComponentModel.DataAnnotations;

namespace MVCApp.Models
{
    public class Item
    {
        public int Id { get; set; }
        
        [Required(ErrorMessage = "Name is Required")]
        [MaxLength(100, ErrorMessage = "Name Must Be Less Than 100 Characters")]
        public string? Name { get; set; }
        
        [Required(ErrorMessage = "Price is Required")]
        public double Price { get; set; }
    }
}