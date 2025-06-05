import java.awt.*;
import java.awt.geom.Rectangle2D;

public class GameScene extends Scene implements ResizableScene {
    Rect background, foreground;
    Snake snake;

    public GameScene() {
        setSize(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);

        snake = new Snake(10, 48, 72, 24, 24);
    }

    @Override
    public void setSize(int width, int height) {
        background = new Rect(0, 0, width, height);

        int tileSize = 24;
        int fgWidth = width - 2 * tileSize;
        int fgHeight = height - 2 * tileSize;

        foreground = new Rect(24, 24, fgWidth, fgHeight);
    }

    @Override
    public void update(double dt) {
        snake.update(dt);
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;

        g2.setColor(Color.BLACK);
        g2.fill(new Rectangle2D.Double(background.x, background.y, background.width, background.height));

        g2.setColor(Color.WHITE);
        g2.fill(new Rectangle2D.Double(foreground.x, foreground.y, foreground.width, foreground.height));

        snake.draw(g2);
    }
}
