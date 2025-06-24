namespace RecursionExamples
{
    public static class Program
    {
        public static void Main(string[] args)
        {
            string binaryNumber = DecimalToBinary(233, "");
            
            Console.WriteLine(binaryNumber);
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