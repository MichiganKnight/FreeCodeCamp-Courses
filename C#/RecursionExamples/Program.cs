namespace RecursionExamples
{
    public static class Program
    {
        public static void Main(string[] args)
        {
            Console.Write("Enter a Number: ");
            string input =  Console.ReadLine();

            if (!string.IsNullOrEmpty(input))
            {
                int decimalNumber = int.Parse(input);
                
                string binaryNumber = DecimalToBinary(decimalNumber, "");
            
                Console.WriteLine(binaryNumber);    
            }
            else
            {
                Console.WriteLine("Enter a Valid Number");
            }
        }

        private static string DecimalToBinary(int number, string result)
        {
            if (number == 0)
            {
                return result;
            }
            
            result = number % 2 + result;

            return DecimalToBinary(number / 2, result);
        }
    }   
}