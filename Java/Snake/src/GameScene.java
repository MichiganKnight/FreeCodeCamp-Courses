import java.awt.*;

public class GameScene extends Scene {
    @Override
    public void update(double dt) {

    }

    @Override
    public void draw(Graphics g) {
        //Set color to Dark Blue
        g.setColor(new Color(0, 0, 139));
        g.fillRect(0, 0,Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
    }
}
