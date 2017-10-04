package nitrodragon.world;

import nitrodragon.collision.AABB;
import nitrodragon.io.Window;
import nitrodragon.render.Camera;
import nitrodragon.render.Shader;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class World {
    private final int view = 24;
    private byte tiles[];
    private AABB[] bounding_boxes;
    private int width;
    private int height;
    private int scale;

    private Matrix4f world;

    public World() {
        width = 64;
        height = 64;
        scale = 16;

        tiles = new byte[width * height];
        bounding_boxes = new AABB[width * height];

        world = new Matrix4f().setTranslation(new Vector3f(0));
        world.scale(scale);
    }

    public void render(TileRenderer render, Shader shader, Camera camera, Window window) {
        int posX = ((int) camera.getPosition().x + (window.getWidth() / 2)) / (scale * 2);
        int posY = ((int) camera.getPosition().y - (window.getHeight() / 2)) / (scale * 2);

        for (int i = 0; i < view; i++) {
            for (int j = 0; j < view; j++) {
                Tile t = getTile(i-posX, j+posY);
                if (t != null)
                    render.renderTile(t, i - posX, -j - posY, shader, world, camera);
            }
        }
    }

    public void correctCamera(Camera camera, Window window) {
        Vector3f pos = camera.getPosition();

        int w = -width * scale * 2;
        int h = height * scale * 2;

        // Center
        if (pos.x > -(window.getWidth() / 2) + scale) {
            pos.x = -(window.getWidth() / 2) + scale; // scale gets the tiles to stop at all the way, not halfway
        }
        if (pos.x < w +(window.getWidth() / 2) + scale) {
            pos.x = w +(window.getWidth() / 2) + scale;
        }
        if (pos.y < (window.getHeight() / 2) - scale) {
            pos.y = (window.getHeight() / 2) - scale;
        }
        if (pos.y > h - (window.getHeight() / 2) - scale) {
            pos.y = h - (window.getHeight() / 2) - scale;
        }
    }

    public void setTile(Tile tile, int x, int y) {
        tiles[x + y * width] = tile.getId();
        if (tile.isSolid()) {
            bounding_boxes[x + y * width] = new AABB(new Vector2f(x*2, -y*2), new Vector2f(1, 1));
        } else {
            bounding_boxes[x + y * width] = null;
        }
    }
    public Tile getTile(int x, int y) {
        try {
            return Tile.tiles[tiles[x + y * width]];
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }
    public AABB getTileBoundingBox(int x, int y) {
        try {
            return bounding_boxes[x + y * width];
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    public int getScale() {
        return scale;
    }
}
