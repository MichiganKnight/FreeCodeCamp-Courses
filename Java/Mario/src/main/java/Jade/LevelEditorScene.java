package Jade;

import Jade.Util.Time;
import Renderer.Shader;
import org.joml.Vector2f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class LevelEditorScene extends Scene {
    private int vertexID, fragmentID, shaderProgram;

    private final float[] vertexArray = {
            // Position // Color
            100.5f, 0.5f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f,  // Bottom Right
            0.5f, 100.5f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f,  // Top Left
            100.5f, 100.5f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f,   // Top Right
            0.5f, 0.5f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f  // Bottom Left
    };

    // IMPORTANT - Counter-Clockwise
    private final int[] elementArray = {
            2, 1, 0,
            0, 1, 3
    };

    private int vaoID, vboID, eboID;

    private Shader defaultShader;

    public LevelEditorScene() {

    }

    @Override
    public void init() {
        this.camera = new Camera(new Vector2f(-200, -300));

        defaultShader = new Shader("Assets/Shaders/Default.glsl");
        defaultShader.compile();

        // Generate VAO, VBO, and EBO Buffer Objects
        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        // Create Float Buffer of Vertices
        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertexArray.length);
        vertexBuffer.put(vertexArray).flip();

        // Create VBO
        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);

        // Create Indices and Upload
        IntBuffer elementBuffer = BufferUtils.createIntBuffer(elementArray.length);
        elementBuffer.put(elementArray).flip();

        eboID = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, elementBuffer, GL_STATIC_DRAW);

        // Add Vertex Attribute Pointers
        int positionsSize = 3;
        int colorSize = 4;
        int floatSizeBytes = 4;
        int vertexSizeBytes = (positionsSize + colorSize) * floatSizeBytes;

        glVertexAttribPointer(0, positionsSize, GL_FLOAT, false, vertexSizeBytes, 0);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, colorSize, GL_FLOAT, false, vertexSizeBytes, positionsSize * floatSizeBytes);
        glEnableVertexAttribArray(1);
    }

    @Override
    public void update(float dt) {
        if (dt > 0.1f) {
            dt = 0.1f;
        }

        // Square Movement
        //camera.position.x -= dt * 50.0f;
        //camera.position.y -= dt * 20.0f;

        defaultShader.use();
        defaultShader.uploadMat4f("uProjection", camera.getProjectionMatrix());
        defaultShader.uploadMat4f("uView", camera.getViewMatrix());
        defaultShader.uploadFloat("uTime", Time.getTime());

        // Bind VAO
        glBindVertexArray(vaoID);

        // Enable Vertex Attribute Pointers
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glDrawElements(GL_TRIANGLES, elementArray.length, GL_UNSIGNED_INT, 0);

        // Unbind Everything
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);

        glBindVertexArray(0);

        defaultShader.detach();
    }
}