import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.KeyEvent;

public class MenuScene extends Scene {
    public KL keyListener;

    public MenuScene(KL keyListener) {
        this.keyListener = keyListener;
    }

    @Override
    public void update(double dt) {
        if (keyListener.isKeyPressed(KeyEvent.VK_UP)) {
            System.out.println("Up Pressed");
        }
    }

    @Override
    public void draw(Graphics g) {
        // Set color to Dark Green
        g.setColor(new Color(0, 100, 0));
        g.fillRect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
    }
}
