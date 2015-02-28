package game;

import static helper.Artist.DrawQuadTex;
import static helper.Artist.LoadTexture;
import org.newdawn.slick.opengl.Texture;

/**
 * The class of tiles.
 * @author Euaggelos
 */
public class Tile {

    private float x, y, width, height;
    private Texture texture;
    private TileType type;

    /**
     * Tile constructor.
     * @param x The position of the tile on the X axis (pixels).
     * @param y This position of the tile on the Y axis (pixels).
     * @param width The width of the tile (pixels).
     * @param height The height of the tile (pixels).
     * @param type The type of the tile.
     */
    public Tile(float x, float y, float width, float height, TileType type) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.type = type;
        this.texture = LoadTexture(type.textureName);
    }

    /**
     * This will draw the something I AM NOT SURE WHAT IT DOES NEEDS TO BE UPDATED.
     */
    public void Draw() {
        DrawQuadTex(texture, x, y, width, height);
    }

    /**
     * This will return the X position of the tile.
     * @return x
     */
    public float getX() {
        return x;
    }

    /**
     * This will return the Y position of the tile.
     * @return y
     */
    public float getY() {
        return y;
    }

    /**
     * This will return the width of the tile.
     * @return width
     */
    public float getWidth() {
        return width;
    }

    /**
     * This will return the height of the tile.
     * @return height
     */
    public float getHeight() {
        return height;
    }

    /**
     * This returns texture of the tile.
     * @return texture
     */
    public Texture getTexture() {
        return texture;
    }

    /**
     * This will return the type of the tile.
     * @return type
     */
    public TileType getType() {
        return type;
    }
}
