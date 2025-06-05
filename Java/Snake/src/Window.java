import javax.swing.*;

public class Window extends JFrame implements Runnable
{
    public Window(int width, int height, String title)
    {
        setSize(width, height);
        setLocationRelativeTo(null);
        setTitle(title);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void run()
    {

    }

    private void Test()
    {

    }
}
