public class Time
{
    public static double timeStarted = System.nanoTime();

    public static double GetTime()
    {
        return System.nanoTime() - timeStarted * 1E-9;
    }
}