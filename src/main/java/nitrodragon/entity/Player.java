package nitrodragon.entity;

import nitrodragon.collision.AABB;
import nitrodragon.collision.Collision;
import nitrodragon.io.Window;
import nitrodragon.render.*;
import nitrodragon.world.World;
import org.joml.Vector2f;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;

public class Player extends Entity {
    public Player(Transform transform) {
        super(new Animation(5, 15, ""), transform);
    }

    @Override
    public void update(float delta, Window window, Camera camera, World world) {
        Vector2f movement = new Vector2f();
        if (window.getInput().isKeyDown(GLFW_KEY_LEFT)) {
            movement.add(-10 * delta, 0);
        }
        if (window.getInput().isKeyDown(GLFW_KEY_RIGHT)) {
            movement.add(10 * delta, 0);
        }
        if (window.getInput().isKeyDown(GLFW_KEY_UP)) {
            movement.add(0, 10 * delta);
        }
        if (window.getInput().isKeyDown(GLFW_KEY_DOWN)) {
            movement.add(0, -10 * delta);
        }
        move(movement);

        camera.getPosition().lerp(transform.pos.mul(-world.getScale(), new Vector3f()), 0.05f);
    }

}
