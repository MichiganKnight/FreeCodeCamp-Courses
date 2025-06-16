package jade;

import components.Sprite;
import components.SpriteRenderer;
import components.Spritesheet;
import org.joml.Vector2f;
import util.AssetPool;

public class LevelEditorScene extends Scene {
    public LevelEditorScene() {

    }

    @Override
    public void init() {
        loadResources();

        this.camera = new Camera(new Vector2f(-250, 0));

        Spritesheet sprites = AssetPool.getSpritesheet("Assets/Images/Spritesheet.png");

        GameObject obj1 = new GameObject("Object 1", new Transform(new Vector2f(100, 100), new Vector2f(256, 256)));
        obj1.addComponent(new SpriteRenderer(sprites.getSprite(0)));
        this.addGameObjectToScene(obj1);

        GameObject obj2 = new GameObject("Object 2", new Transform(new Vector2f(400, 100), new Vector2f(256, 256)));
        obj2.addComponent(new SpriteRenderer(sprites.getSprite(1)));
        this.addGameObjectToScene(obj2);
    }

    private void loadResources() {
        AssetPool.getShader("Assets/Shaders/Default.glsl");

        AssetPool.addSpritesheet("Assets/Images/Spritesheet.png", new Spritesheet(AssetPool.getTexture("Assets/Images/Spritesheet.png"), 16, 16, 26, 0));
    }

    @Override
    public void update(float dt) {
        System.out.println("FPS: " + (1.0f / dt));

        if (dt > 0.1f) {
            dt = 0.1f;
        }

        // Square Movement
        //camera.position.x -= dt * 50.0f;
        //camera.position.y -= dt * 20.0f;

        for (GameObject go : this.gameObjects) {
            go.update(dt);
        }

        this.renderer.render();
    }
}