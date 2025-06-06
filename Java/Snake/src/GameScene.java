import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;

public class GameScene extends Scene implements ResizableScene {
    Rect background, foreground;
    Snake snake;
    KL keyListener;

    public Food food;

    public GameScene(KL keyListener) {
        setSize(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);

        snake = new Snake(10, 48, 72, Constants.TILE_WIDTH, Constants.TILE_HEIGHT, foreground);

        this.keyListener = keyListener;

        food = new Food(foreground, snake, 12, 12, Color.GREEN);
        food.spawn();
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
        if (keyListener.isKeyPressed(KeyEvent.VK_W) || keyListener.isKeyPressed(KeyEvent.VK_UP)) {
            snake.changeDirection(Direction.UP);
        } else if (keyListener.isKeyPressed(KeyEvent.VK_S) || keyListener.isKeyPressed(KeyEvent.VK_DOWN)) {
            snake.changeDirection(Direction.DOWN);
        } else if (keyListener.isKeyPressed(KeyEvent.VK_A) || keyListener.isKeyPressed(KeyEvent.VK_LEFT)) {
            snake.changeDirection(Direction.LEFT);
        } else if (keyListener.isKeyPressed(KeyEvent.VK_D) || keyListener.isKeyPressed(KeyEvent.VK_RIGHT)) {
            snake.changeDirection(Direction.RIGHT);
        }

        if (!food.isSpawned) {
            food.spawn();
        }

        food.update(dt);
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
        food.draw(g2);
    }
}
