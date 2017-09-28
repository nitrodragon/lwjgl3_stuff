package nitrodragon.world;

public class Tile {
    public static Tile[] tiles = new Tile[16];

    public static final Tile test_tile = new Tile((byte) 0, "grass");

    private byte id;
    private String texture;

    public Tile(byte id, String texture) {
        this.id = id;
        this.texture = texture;
        if (tiles[id] != null) {
            throw new IllegalStateException("Tiles at[" + id +"] is already in use!");
        }
        tiles[id] = this;
    }

    public byte getId() {
        return id;
    }

    public String getTexture() {
        return texture;
    }
}
