import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class MenuScene extends Scene {
    public KL keyListener;
    public ML mouseListener;
    public BufferedImage title, play, playPressed, exit, exitPressed;
    public Rect titleRect, playRect, exitRect;

    public BufferedImage playCurrentImage, exitCurrentImage;

    public Cursor defaultCursor = Cursor.getDefaultCursor();
    public Cursor handCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
    public Cursor currentCursor = Cursor.getDefaultCursor();

    public MenuScene(KL keyListener, ML mouseListener) {
        this.keyListener = keyListener;
        this.mouseListener = mouseListener;

        try {
            BufferedImage spritesheet = ImageIO.read(getClass().getResource("/assets/MenuSprite.png"));

            title = spritesheet.getSubimage(0, 242, 960, 240);
            play = spritesheet.getSubimage(0, 121, 261, 121);
            playPressed = spritesheet.getSubimage(264, 121, 261, 121);
            exit = spritesheet.getSubimage(0, 0, 233, 93);
            exitPressed = spritesheet.getSubimage(264, 0, 233, 93);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        playCurrentImage = play;
        exitCurrentImage = exit;

        titleRect = new Rect(490, 40, 300, 100);
        playRect = new Rect(560, 200, 150, 70);
        exitRect = new Rect(568, 280, 130, 55);
    }

    @Override
    public void update(double dt) {
        boolean hoveringPlay = mouseListener.getX() >= playRect.x && mouseListener.getX() <= playRect.x + playRect.width && mouseListener.getY() >= playRect.y && mouseListener.getY() <= playRect.y + playRect.height;
        boolean hoveringExit = mouseListener.getX() >= exitRect.x && mouseListener.getX() <= exitRect.x + exitRect.width && mouseListener.getY() >= exitRect.y && mouseListener.getY() <= exitRect.y + exitRect.height;

        if (hoveringPlay) {
            playCurrentImage = playPressed;

            if (mouseListener.isPressed()) {
                Window.getWindow().changeState(1);

                Window.getWindow().setCursor(defaultCursor);
            }
        } else {
            playCurrentImage = play;
        }

        if (hoveringExit) {
            exitCurrentImage = exitPressed;

            if (mouseListener.isPressed()) {
                Window.getWindow().close();
            }
        } else {
            exitCurrentImage = exit;
        }

        Cursor targetCursor = (hoveringPlay || hoveringExit) ? handCursor : defaultCursor;

        if (currentCursor != targetCursor) {
            Window.getWindow().setCursor(targetCursor);

            currentCursor = targetCursor;
        }
    }

    @Override
    public void draw(Graphics g) {
        // Set color to White
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);

        g.drawImage(title, (int) titleRect.x, (int) titleRect.y, (int) titleRect.width, (int) titleRect.height, null);
        g.drawImage(playCurrentImage, (int) playRect.x, (int) playRect.y, (int) playRect.width, (int) playRect.height, null);
        g.drawImage(exitCurrentImage, (int) exitRect.x, (int) exitRect.y, (int) exitRect.width, (int) exitRect.height, null);
    }
}
