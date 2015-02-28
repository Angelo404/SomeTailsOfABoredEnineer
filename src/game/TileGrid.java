package game;

import static helper.Artist.*;

/**
 *
 * @author Euaggelos
 */
public class TileGrid {

    /**
     * Tile 2d list.
     */
    public Tile[][] map;
    /**
     * Size of the tiles.
     */
    static final int TILESIZE = 32;
    /**
     * Dimensions of the cylinder in tiles.
     */
    public static final int GRIDY = 40, GRIDX = 120;
    /**
     * Dimensions of the cylinder in pixels.
     */
    public static final int GRIDYpix = GRIDY * TILESIZE, GRIDXpix = GRIDX * TILESIZE;
    /**
     * Half size of the screen in tiles.
     */
    public static final int HALFSCREENX = WIDTH / (TILESIZE * 2), HALFSCREENY = HEIGHT / (TILESIZE * 2);
    /**
     * Half size of the screen in pixels.
     */
    public static final int HALFSCREENXpix = HALFSCREENX * TILESIZE, HALFSCREENYpix = HALFSCREENY * TILESIZE;

    /**
     * This will translate the integer grid into a texture grid.
     *
     * @param newMap
     */
    public TileGrid(int[][] newMap) {
        map = new Tile[GRIDX][GRIDY];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                switch (newMap[j][i]) {
                    case 0:
                        map[i][j] = new Tile(i * TILESIZE, j * TILESIZE, TILESIZE, TILESIZE, TileType.Grass);
                        break;
                    case 1:
                        map[i][j] = new Tile(i * TILESIZE, j * TILESIZE, TILESIZE, TILESIZE, TileType.Dirt);
                        break;
                    case 2:
                        map[i][j] = new Tile(i * TILESIZE, j * TILESIZE, TILESIZE, TILESIZE, TileType.Water);
                        break;
                    case 3:
                        map[i][j] = new Tile(i * TILESIZE, j * TILESIZE, TILESIZE, TILESIZE, TileType.Stone);
                        break;
                    case 50:
                        map[i][j] = new Tile(i * TILESIZE, j * TILESIZE, TILESIZE, TILESIZE, TileType.Street);
                        break;
                    case 51:
                        map[i][j] = new Tile(i * TILESIZE, j * TILESIZE, TILESIZE, TILESIZE, TileType.Street2);
                        break;
                    case 98:
                        map[i][j] = new Tile(i * TILESIZE, j * TILESIZE, TILESIZE, TILESIZE, TileType.GrassGateLeft);
                        break;
                    case 99:
                        map[i][j] = new Tile(i * TILESIZE, j * TILESIZE, TILESIZE, TILESIZE, TileType.GrassGateRight);
                        break;
                }
            }
        }
    }

    /**
     * This will set a tile at the given location.
     *
     * @param xCoord Tile position (X axis).
     * @param yCoord Tile position (Y axis).
     * @param type Type of tile.
     */
    public void SetTile(int xCoord, int yCoord, TileType type) {
        map[xCoord][yCoord] = new Tile(xCoord * TILESIZE, yCoord * TILESIZE, TILESIZE, TILESIZE, type);
    }

    /**
     * This will get the type of a tile at the given location.
     *
     * @param xCoord Tile position (X axis).
     * @param yCoord Tile position (Y axis).
     * @return
     */
    public Tile GetTile(int xCoord, int yCoord) {
        return map[xCoord][yCoord];
    }

    /**
     * This will loop through the grid and draw the grid around the player.
     * count: Used instead of the i inside the loop for the X axis of the
     * cylinder. count2: Used for correction of the center of the Y axis of the
     * cylinder. --- If: checks for the left side of the cylinder. Else if:
     * checks for the right side of the cylinder. --- If: checks for the top
     * side of the cylinder. Else if: checks for the bottom side of the
     * cylinder. Else: checks for the middle of the cylinder.
     *
     * @param x Position of the player on the X axis (tile).
     * @param y Position of the player on the Y axis (tile).
     */
    public void Draw(float x, float y) {
        try {
            int count, count2;      // COUNT -> ANTI GIA i DEKSIA ARISTERA KILINDROU // COUNT2 -> DIORTHOSH KENTROU EKTIPOSIS KILINDROU (PANW KATW)
            float n;
            for (int i = (int) x - HALFSCREENXpix; i < x + HALFSCREENXpix + 32; i += 32) {
                count = i;
                if (count < 0) {                        // ELENXOS GIA ARISTERA TOU KILINDROU
                    count = GRIDXpix + count;
                } else if (count >= GRIDXpix) {    // ELENXOS GIA DEKSIA TOU KILINDROU
                    count = count - GRIDXpix;
                }
                if (y < HALFSCREENYpix) {              // ELENXOS GIA TO PANW TOU KILINDROU
                    count2 = HALFSCREENYpix - (int) y;
                    n = 0;
                } else if (y > GRIDYpix - HALFSCREENYpix) { // ELENXOS GIA TO KATW TOU KILINDROU
                    count2 = GRIDYpix - HALFSCREENYpix - (int) y - 32;
                    n = -32;
                } else {                            // GIA TH MESH TOU KILINDROU

                    count2 = 0;
                    n = -y % 32;
                }
                for (int j = (int) y - HALFSCREENYpix + count2; j < y + HALFSCREENYpix + count2; j += 32) {
                    //System.out.println(y);
                    Tile t = map[count / 32][(int) (j / 32)];
                    DrawQuadTex(t.getTexture(), (i - x + HALFSCREENXpix) - ((int) x) % 32, (j - y + HALFSCREENYpix - count2) + n, t.getWidth(), t.getHeight());//
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("!");
        }
    }
}
