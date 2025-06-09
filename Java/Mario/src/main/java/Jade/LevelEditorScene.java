package Jade;

import static org.lwjgl.opengl.GL20.*;

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
    }

    @Override
    public void update(float dt) {

    }
}