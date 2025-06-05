package Jade;

public class Window {
    private int width, height;

    private String title;

    private static Window window = null;

    private Window() {
        this.width = 1920;
        this.height = 1080;

        this.title = "Mario";
    }

    public static Window Get() {
        if (Window.window == null)  {
            Window.window = new Window();
        }

        return Window.window;
    }

    public void Run() {

    }
}
