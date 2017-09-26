package initialtest;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import org.joml.Matrix4f;
import org.joml.Vector3f;
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

        Camera camera = new Camera(WIDTH, HEIGHT);
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

        Texture tex = new Texture("./res/BigIra.png");

        Matrix4f scale = new Matrix4f()
                .translate(new Vector3f(100, 0, 0))
                .scale(128);

        Matrix4f target;

        camera.setPosition(new Vector3f(-100, 0, 0));

        while (!glfwWindowShouldClose(window)) {
            // EVERY time
            target = scale;
            if(glfwGetKey(window, GLFW_KEY_ESCAPE) == GL_TRUE) {
                glfwSetWindowShouldClose(window, true);
            }

            glfwPollEvents();

            glClear(GL_COLOR_BUFFER_BIT);

            shader.bind();
            shader.setUniform("sampler", 0);
            shader.setUniform("projection", camera.getProjection().mul(target));
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
