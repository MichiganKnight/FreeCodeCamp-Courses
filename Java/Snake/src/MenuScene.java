import java.awt.Graphics;
import java.awt.Color;

public class MenuScene extends Scene {
    @Override
    public void update(double dt) {

    }

    @Override
    public void draw(Graphics g) {
        // Set color to Dark Green
        g.setColor(new Color(0, 100, 0));
        g.fillRect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
    }
}
