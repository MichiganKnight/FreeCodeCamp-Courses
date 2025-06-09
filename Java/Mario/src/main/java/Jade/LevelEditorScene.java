package Jade;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class LevelEditorScene extends Scene {
    private String vertexShaderSrc = """
            #version 330 core
            
            layout (location=0) in vec3 aPos;
            layout (location=1) in vec4 aColor;
            
            out vec4 fColor;
            
            void main() {
                fColor = aColor;
                gl_Position = vec4(aPos, 1.0);
            }""";
    private String fragmentShaderSrc = """
            #version 330 core
            
            in vec4 fColor;
            
            out vec4 color;
            
            void main() {
                color = fColor;
            }""";

    private int vertexID, fragmentID, shaderProgram;

    private float[] vertexArray = {
        // Position // Color
        0.5f, -0.5f, 0.0f,   1.0f, 0.0f, 0.0f, 1.0f,  // Bottom Right
        -0.5f, 0.5f, 0.0f,   0.0f, 1.0f, 0.0f, 1.0f,  // Top Left
        0.5f, 0.5f, 0.0f,    0.0f, 0.0f, 1.0f, 1.0f,   // Top Right
        -0.5f, -0.5f, 0.0f,  1.0f, 1.0f, 0.0f, 1.0f  // Bottom Left
    };

    // IMPORTANT - Counter-Clockwise
    private int[] elementArray = {
        2, 1, 0,
        0, 1, 3
    };

    private int vaoID, vboID, eboID;

    public LevelEditorScene() {

    }

    @Override
    public void init() {
        // Compile and Link Shaders

        // Load and Compile Vertex Shader
        vertexID = glCreateShader(GL_VERTEX_SHADER);

        // Pass Shader Source to GPU
        glShaderSource(vertexID, vertexShaderSrc);
        glCompileShader(vertexID);

        // Check for Errors
        int success = glGetShaderi(vertexID, GL_COMPILE_STATUS);

        if (success == GL_FALSE) {
            int length = glGetShaderi(vertexID, GL_INFO_LOG_LENGTH);

            System.out.println("ERROR: 'default.glsl'\n\tVertex Shader Compilation Failed");
            System.out.println(glGetShaderInfoLog(vertexID, length));

            assert false: "";
        }

        // Load and Compile Vertex Shader
        fragmentID = glCreateShader(GL_FRAGMENT_SHADER);

        // Pass Shader Source to GPU
        glShaderSource(fragmentID, fragmentShaderSrc);
        glCompileShader(fragmentID);

        // Check for Errors
        success = glGetShaderi(fragmentID, GL_COMPILE_STATUS);

        if (success == GL_FALSE) {
            int length = glGetShaderi(fragmentID, GL_INFO_LOG_LENGTH);

            System.out.println("ERROR: 'default.glsl'\n\tFragment Shader Compilation Failed");
            System.out.println(glGetShaderInfoLog(fragmentID, length));

            assert false: "";
        }

        // Link Shaders and Check for Errors
        shaderProgram = glCreateProgram();
        glAttachShader(shaderProgram, vertexID);
        glAttachShader(shaderProgram, fragmentID);
        glLinkProgram(shaderProgram);

        // Check for Linking Errors
        success = glGetProgrami(shaderProgram, GL_LINK_STATUS);

        if (success == GL_FALSE) {
            int length = glGetProgrami(shaderProgram, GL_INFO_LOG_LENGTH);

            System.out.println("ERROR: 'default.glsl'\n\tLinking of Shaders Failed");
            System.out.println(glGetProgramInfoLog(shaderProgram, length));

            assert false: "";
        }

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
        // Bind Shader Program
        glUseProgram(shaderProgram);

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

        glUseProgram(0);
    }
}