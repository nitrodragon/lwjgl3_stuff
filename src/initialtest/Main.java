package initialtest;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.GL;

public class Main {

    public Main() {
        if (!glfwInit()) {
            throw new IllegalStateException("GLFW failed to initialize!");
        }

        long window = glfwCreateWindow(640, 480, "Windows", 0, 0);

        glfwShowWindow(window);
        glfwMakeContextCurrent(window);
        GL.createCapabilities();

        glEnable(GL_TEXTURE_2D);

        float[] vertices = new float[] {
                -0.5f, 0.5f, // Top left
                0.5f, 0.5f,  // top right
                0.5f, -0.5f, // bottom right
                -0.5f, -0.5f, // bottom left
        };

        float[] texture = new float[] {
                0,0,
                1,0,
                1,1,
                0,1
        };

        int[] indices = new int[] {
                0, 1, 2,
                2, 3, 0
        };

        Model model = new Model(vertices, texture, indices);
        Shader shader = new Shader("shader");
        // Texture tex = new Texture("./res/BigIra.png");

        while (!glfwWindowShouldClose(window)) {
            glfwPollEvents();

            glClear(GL_COLOR_BUFFER_BIT);

            // tex.bind();
            shader.bind();
            shader.setUniform("green", 1);
            model.render();

            glfwSwapBuffers(window);
        }
        glfwTerminate();
    }

    public static void main(String[] args) {
        new Main();
    }
}
