package initialtest;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL;

public class Main {

    static int WIDTH = 640;
    static int HEIGHT = 480;

    public Main() {
        if (!glfwInit()) {
            throw new IllegalStateException("GLFW failed to initialize!");
        }

        long window = glfwCreateWindow(WIDTH, HEIGHT, "Windows", 0, 0);

        glfwShowWindow(window);
        glfwMakeContextCurrent(window);
        GL.createCapabilities();

        glEnable(GL_TEXTURE_2D);

        float[] vertices = new float[] {
                -0.5f, 0.5f, 0,
                0.5f, 0.5f, 0,
                0.5f, -0.5f, 0,
                -0.5f, -0.5f, 0,
         };

         float[] texture = new float[] {
                0,0,
                1,0,
                1,1,
                0,1
         };

         int[] indices = new int[] {
                0,1,2,
                2,3,0
         };

        Model model = new Model(vertices, texture, indices);
        Shader shader = new Shader("shader");

        Texture tex = new Texture("./res/256fx256f.jpg");

        // Divide by 2 to set origin to 0; center
        Matrix4f projection = new Matrix4f()
                .ortho2D(WIDTH / 2, -WIDTH / 2, -HEIGHT / 2, HEIGHT / 2);
        Matrix4f scale = new Matrix4f().scale(128);

        Matrix4f target = new Matrix4f();

        projection.mul(scale, target);

        while (!glfwWindowShouldClose(window)) {

            if(glfwGetKey(window, GLFW_KEY_ESCAPE) == GL_TRUE) {
                glfwSetWindowShouldClose(window, true);
            }

            glfwPollEvents();

            glClear(GL_COLOR_BUFFER_BIT);

            shader.bind();
            shader.setUniform("sampler", 0);
            shader.setUniform("projection", target);
            tex.bind(0);
            model.render();

            glfwSwapBuffers(window);
        }
        glfwTerminate();
    }

    public static void main(String[] args) {
        new Main();
    }
}
