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

            int[] data = [-5, 20, 10, 3, 2, 0];
            
            MergeSort(data, 0, data.Length - 1);
            
            Console.WriteLine($"Sorted Data: {string.Join(", ", data)}");
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
        
        private static void MergeSort(int[] data, int start, int end)
        {
            if (start < end)
            {
                int mid = (start + end) / 2;
                
                MergeSort(data, start, mid);
                MergeSort(data, mid + 1, end);
                
                Merge(data, start, mid, end);
            }
        }

        private static void Merge(int[] data, int start, int mid, int end)
        {
            // Build Temporary Array
            int[] temp = new int[end - start + 1];

            int i = start, j = mid + 1, k = 0;

            // While Both Sub-Arrays Have Values - Merge Them in Order
            while (i <= mid && j <= end)
            {
                if (data[i] <= data[j])
                {
                    temp[k++] = data[i++];
                }
                else
                {
                    temp[k++] = data[j++];
                }
            }

            // Add Rest of Values From Left Sub-Array
            while (i <= mid)
            {
                temp[k++] = data[i++];
            }

            // Add Rest of Values From Right Sub-Array
            while (j <= end)
            {
                temp[k++] = data[j++];
            }

            for (i = start; i <= end; i++)
            {
                data[i] = temp[i - start];
            }
        }
    }   
}