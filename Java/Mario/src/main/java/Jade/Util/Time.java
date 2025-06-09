package Jade.Util;

public class Time {
    public static float timeStarted = System.nanoTime();

    /**
     * Simple static function used to get the time
     * @return Returns time as a float
     */
    public static float getTime() {
        return (float) (System.nanoTime() - timeStarted * 1E-9);
    }
}
