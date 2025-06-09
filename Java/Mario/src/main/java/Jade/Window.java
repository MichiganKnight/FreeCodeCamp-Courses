package Jade;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
    private int width, height;
    private String title;
    private long glfwWindow;

    private static Window window = null;

    private Window() {
        this.width = 1920;
        this.height = 1080;

        this.title = "Mario";
    }

    public static Window get() {
        if (Window.window == null) {
            Window.window = new Window();
        }

        return Window.window;
    }

    public void run() {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");

        init();
        loop();

        // Free the Memory
        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);

        // Terminate GLFW and Free Error Callbacks
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    public void init() {
        // Setup Error Callback
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to Initialize GLFW");
        }

        // Configure GLFW
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);

        // Create the Window
        glfwWindow = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);

        glfwSetWindowSizeCallback(glfwWindow, (window, newWidth, newHeight) -> {
            this.width = newWidth;
            this.height = newHeight;

            glViewport(0, 0, newWidth, newHeight);
        });

        if (glfwWindow == NULL) {
            throw new IllegalStateException("Failed to Create GLFW Window");
        }

        glfwSetCursorPosCallback(glfwWindow, MouseListener::mousePosCallback);
        glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback);
        glfwSetScrollCallback(glfwWindow, MouseListener::mouseScrollCallback);

        glfwSetKeyCallback(glfwWindow, KeyListener::keyCallback);

        // Make OpenGL Context Current
        glfwMakeContextCurrent(glfwWindow);

        // Enable V-Sync
        glfwSwapInterval(1);

        // Make Window Visible
        glfwShowWindow(glfwWindow);

        GL.createCapabilities();

        glViewport(0, 0, width, height);
    }

    public void loop() {
        while (!glfwWindowShouldClose(glfwWindow)) {
            // Poll Events
            glfwPollEvents();

            glClearColor(1.0f, 0.0f, 0.0f, 1.0f);
            glClear(GL_COLOR_BUFFER_BIT);

            if (KeyListener.isKeyPressed(GLFW_KEY_SPACE)) {
                System.out.println("Space Pressed");
            }

            glfwSwapBuffers(glfwWindow);
        }
    }
}
