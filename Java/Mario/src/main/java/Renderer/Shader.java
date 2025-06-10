package Renderer;

import org.joml.*;
import org.lwjgl.BufferUtils;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;

public class Shader {
    private int shaderProgramID;
    private boolean beingUsed = false;

    private String vertexSource;
    private String fragmentSource;
    private final String filePath;

    public Shader(String filePath) {
        this.filePath = filePath;

        try {
            String source = new String(Files.readAllBytes(Paths.get(filePath)));
            String[] splitString = source.split("(#type)( )+([a-zA-Z]+)");

            // Find First Pattern After #type 'Pattern'
            int index = source.indexOf("#type") + 6;
            int eol = source.indexOf("\r\n", index);

            String firstPattern = source.substring(index, eol).trim();

            // Find Second Pattern After #type 'Pattern'
            index = source.indexOf("#type", eol) + 6;
            eol = source.indexOf("\r\n", index);

            String secondPattern = source.substring(index, eol).trim();

            if (firstPattern.equals("vertex")) {
                vertexSource = splitString[1];
            } else if (firstPattern.equals("fragment")) {
                fragmentSource = splitString[1];
            } else {
                throw new IOException("Unexpected Token: " + firstPattern);
            }

            if (secondPattern.equals("vertex")) {
                vertexSource = splitString[2];
            } else if (secondPattern.equals("fragment")) {
                fragmentSource = splitString[2];
            } else {
                throw new IOException("Unexpected Token: " + secondPattern);
            }

        } catch (IOException e) {
            e.printStackTrace();

            assert false : "Error: Could not Open File for Shader: " + filePath;
        }
    }

    public void compile() {
        int vertexID;
        int fragmentID;

        // Compile and Link Shaders

        // Load and Compile Vertex Shader
        vertexID = glCreateShader(GL_VERTEX_SHADER);

        // Pass Shader Source to GPU
        glShaderSource(vertexID, vertexSource);
        glCompileShader(vertexID);

        // Check for Errors
        int success = glGetShaderi(vertexID, GL_COMPILE_STATUS);

        if (success == GL_FALSE) {
            int length = glGetShaderi(vertexID, GL_INFO_LOG_LENGTH);

            System.out.println("ERROR: '" + filePath + "'\n\tVertex Shader Compilation Failed");
            System.out.println(glGetShaderInfoLog(vertexID, length));

            assert false : "";
        }

        // Load and Compile Vertex Shader
        fragmentID = glCreateShader(GL_FRAGMENT_SHADER);

        // Pass Shader Source to GPU
        glShaderSource(fragmentID, fragmentSource);
        glCompileShader(fragmentID);

        // Check for Errors
        success = glGetShaderi(fragmentID, GL_COMPILE_STATUS);

        if (success == GL_FALSE) {
            int length = glGetShaderi(fragmentID, GL_INFO_LOG_LENGTH);

            System.out.println("ERROR: '" + filePath + "'\n\tFragment Shader Compilation Failed");
            System.out.println(glGetShaderInfoLog(fragmentID, length));

            assert false : "";
        }

        // Link Shaders and Check for Errors
        shaderProgramID = glCreateProgram();
        glAttachShader(shaderProgramID, vertexID);
        glAttachShader(shaderProgramID, fragmentID);
        glLinkProgram(shaderProgramID);

        // Check for Linking Errors
        success = glGetProgrami(shaderProgramID, GL_LINK_STATUS);

        if (success == GL_FALSE) {
            int length = glGetProgrami(shaderProgramID, GL_INFO_LOG_LENGTH);

            System.out.println("ERROR: '" + filePath + "'\n\tLinking of Shaders Failed");
            System.out.println(glGetProgramInfoLog(shaderProgramID, length));

            assert false : "";
        }
    }

    public void use() {
        // Bind Shader Program
        if (!beingUsed) {
            glUseProgram(shaderProgramID);

            beingUsed = true;
        }
    }

    public void detach() {
        glUseProgram(0);

        beingUsed = false;
    }

    public void uploadMat4f(String varName, Matrix4f mat4) {
        int varLocation = glGetUniformLocation(shaderProgramID, varName);

        use();

        FloatBuffer matBuffer = BufferUtils.createFloatBuffer(16);
        mat4.get(matBuffer);

        glUniformMatrix4fv(varLocation, false, matBuffer);
    }

    public void uploadMat3f(String varName, Matrix3f mat3) {
        int varLocation = glGetUniformLocation(shaderProgramID, varName);

        use();

        FloatBuffer matBuffer = BufferUtils.createFloatBuffer(9);
        mat3.get(matBuffer);

        glUniformMatrix3fv(varLocation, false, matBuffer);
    }

    public void uploadVec4f(String varName, Vector4f vec) {
        int varLocation = glGetUniformLocation(shaderProgramID, varName);

        use();

        glUniform4f(varLocation, vec.x, vec.y, vec.z, vec.w);
    }

    public void uploadVec3f(String varName, Vector3f vec) {
        int varLocation = glGetUniformLocation(shaderProgramID, varName);

        use();

        glUniform3f(varLocation, vec.x, vec.y, vec.z);
    }

    public void uploadVec2f(String varName, Vector2f vec) {
        int varLocation = glGetUniformLocation(shaderProgramID, varName);

        use();

        glUniform2f(varLocation, vec.x, vec.y);
    }

    public void uploadFloat(String varName, float val) {
        int varLocation = glGetUniformLocation(shaderProgramID, varName);

        use();

        glUniform1f(varLocation, val);
    }

    public void uploadInt(String varName, int val) {
        int varLocation = glGetUniformLocation(shaderProgramID, varName);

        use();

        glUniform1i(varLocation, val);
    }

    public void uploadTexture(String varName, int slot) {
        int varLocation = glGetUniformLocation(shaderProgramID, varName);

        use();

        glUniform1i(varLocation, slot);
    }
}
