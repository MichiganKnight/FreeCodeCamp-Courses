namespace VideoConvert
{
    public enum LogType
    {
        Info,
        Warning,
        Error,
        Success
    }

    public static class Logger
    {
        private static string GetLogString(LogType type)
        {
            return type switch
            {
                LogType.Info => "[Info]",
                LogType.Warning => "[Warning]",
                LogType.Error => "[Error]",
                LogType.Success => "[Success]",
                _ => "[Info]"
            };
        }

        private static ConsoleColor GetLogColor(LogType type)
        {
            return type switch
            {
                LogType.Info => ConsoleColor.Cyan,
                LogType.Warning => ConsoleColor.Yellow,
                LogType.Error => ConsoleColor.Red,
                LogType.Success => ConsoleColor.Green,
                _ => ConsoleColor.White
            };
        }

        public static void LogMessage(LogType type, string message)
        {
            Console.ForegroundColor = GetLogColor(type);
            Console.WriteLine($"{GetLogString(type)}: {message}");
            Console.ResetColor();
        }
    }
}