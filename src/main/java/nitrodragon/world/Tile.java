package nitrodragon.world;

public class Tile {
    public static Tile[] tiles = new Tile[16];
    public static byte number_of_tiles = 0;

    public static final Tile test_tile = new Tile("grass");
    public static final Tile test_2 = new Tile("checker");

    private byte id;
    private String texture;

    public Tile(String texture) {
        this.id = number_of_tiles;
        number_of_tiles++;
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
