namespace RecursionExamples
{
    public static class Program
    {
        public static void Main(string[] args)
        {
            Console.Write("Enter a Number: ");
            string? input =  Console.ReadLine();

            if (!string.IsNullOrEmpty(input))
            {
                int decimalNumber = int.Parse(input);
                
                string binaryNumber = DecimalToBinary(decimalNumber, "");
            
                Console.WriteLine($"Binary of {decimalNumber}: {binaryNumber}");
                
                int sum = RecursiveSummation(decimalNumber);
                
                Console.WriteLine($"Recursive Sum of {decimalNumber}: {sum}");
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

        private static int RecursiveSummation(int inputNumber)
        {
            if (inputNumber <= 1)
            {
                return inputNumber;
            }
            
            return inputNumber + RecursiveSummation(inputNumber - 1);
        }

        private static int BinarySearch(int[] a, int left, int right, int x)
        {
            if (left > right)
            {
                return -1;
            }
            
            int mid = (left + right) / 2;

            if (x == a[mid])
            {
                return mid;
            }

            if (x < a[mid])
            {
                return BinarySearch(a, left, mid - 1, x);
            }
            
            return BinarySearch(a, mid + 1, right, x);
        }
    }   
}