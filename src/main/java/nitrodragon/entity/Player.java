package nitrodragon.entity;

import nitrodragon.io.Window;
import nitrodragon.render.Camera;
import nitrodragon.render.Model;
import nitrodragon.render.Shader;
import nitrodragon.render.Texture;
import nitrodragon.world.World;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;

public class Player {
    private Model model;
    private Texture texture;
    private Transform transform;


    public Player() {

        float[] vertices = new float[] {
                -1f, 1f,  0,
                1f, 1f,  0,
                1f, -1f, 0,
                -1f, -1f, 0,
        };

        float[] texture = new float[] {
                0, 0,
                1, 0,
                1, 1,
                0, 1
        };

        int[] indices = new int[] {
                0, 1, 2,
                2, 3, 0
        };
        model = new Model(vertices, texture, indices);
        this.texture = new Texture("wayne.png");

        transform = new Transform();
        transform.scale = new Vector3f(16, 16, 1);
    }

    public void update(float delta, Window window, Camera camera, World world) {
        if (window.getInput().isKeyDown(GLFW_KEY_A)) {
            transform.pos.add(new Vector3f(-10*delta, 0, 0));
        }
        if (window.getInput().isKeyDown(GLFW_KEY_D)) {
            transform.pos.add(new Vector3f(10*delta, 0, 0));
        }
        if (window.getInput().isKeyDown(GLFW_KEY_W)) {
            transform.pos.add(new Vector3f(0, 10*delta, 0));
        }
        if (window.getInput().isKeyDown(GLFW_KEY_S)) {
            transform.pos.add(new Vector3f(0, -10*delta, 0));
        }

        camera.setPosition(transform.pos.mul(-world.getScale(), new Vector3f()));
    }
    public void render(Shader shader, Camera camera) {
        shader.bind();
        shader.setUniform("sampler", 0);
        shader.setUniform("projection", transform.getProjection(camera.getProjection()));
        texture.bind(0);
        model.render();
    }
}
