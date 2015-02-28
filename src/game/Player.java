package game;

import static game.TileGrid.GRIDXpix;
import org.newdawn.slick.opengl.Texture;
import static helper.Artist.*;
import static game.TileGrid.GRIDYpix;
import static game.TileGrid.TILESIZE;

/**
 *
 * @author Euaggelos
 */
public class Player {

    private final int width, height;
    private float x, y;
    private final Texture texture;
    private boolean first = true;
    private int dir = 4;
    private TileGrid grid;
    private float var = 0;

    /**
     *
     * @param texture The texture of the player.
     * @param width The width of the player.
     * @param height The height of the player.
     * @param grid The map grid.
     */
    public Player(Texture texture, int width, int height, TileGrid grid) {
        this.texture = texture;
        this.x = 480;
        this.y = 320;
        this.width = width;
        this.height = height;
        this.grid = grid;
    }

    /**
     * USELESS FOR NOW
     */
    public void Update() {
        if (first) {
            first = false;
        }
    }

    /**
     * This will move the player on the X axis (pixels). If: Player goes at the
     * right end of the cylinder. Else if: Player goes at the left side of the
     * cylinder.
     *
     * @param i
     */
    public void xAxis(float i) {
        if (pathContinues()) {
            x += i;
            if (x > GRIDXpix) {
                x -= GRIDXpix;
            } else if (x < 0) {
                x = GRIDXpix - x;
            }
        }
    }

    /**
     * This will move the player on the Y axis (pixels). If: checks the top
     * border of the cylinder. Else if: checks the bottom border of the cylinder
     *
     * @param i
     */
    public void yAxis(float i) {
        if (pathContinues()) {
            y += i;
            if (y < 0) {
                y = 0;
            } else if (y > GRIDYpix - TILESIZE) {
                y = GRIDYpix - TILESIZE;
            }
        }
    }

    /**
     * This will check if the path in front of the player continues or not.
     *
     * @return answer
     */
    public boolean pathContinues() {
        boolean answer = true;
        int coordx = 0, coordy = 0;

        switch (dir) {
            case 1:
                coordx = 8;
                coordy = -8;
                break;
            case 2:         // +x
                coordx = 8;
                coordy = 0;
                break;
            case 3:
                coordx = 8;
                coordy = 8;
                break;
            case 4:         // +y
                coordx = 0;
                coordy = 8;
                break;
            case 5:
                coordx = -8;
                coordy = 8;
                break;
            case 6:         // -x
                coordx = -8;
                coordy = 0;
                break;
            case 7:
                coordx = -8;
                coordy = -8;
                break;
            case 8:         // -y
                coordx = 0;
                coordy = -8;
                break;
            default:
                System.out.println("YOUFUCKEDUP");
        }
        //Tile myTile = grid.GetTile((int) ((x + 16) / 32), (int) ((y + 16) / 32));
        try {
            Tile nextTile1 = grid.GetTile((int) ((x + 16 + coordx) / 32), (int) ((y + 16 + coordy) / 32));
            if (TileType.Water == nextTile1.getType()) {
                answer = false;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            answer = true;
        }
        return answer;
    }

    /**
     * This will return the X position of the player (pixels).
     *
     * @return x
     */
    public float getX() {
        return x;
    }

    /**
     * This will set the X position of the player (pixels).
     *
     * @param newx
     */
    public void setX(float newx) {
        x = newx;
    }

    /**
     * This will return the Y position of the player (pixels).
     *
     * @return y
     */
    public float getY() {
        return y;
    }

    /**
     * This will set the Y position of the player (pixels).
     *
     * @param newy
     */
    public void setY(float newy) {
        y = newy;
    }

    /**
     * This will return the direction of the player (values from 1 to 8).
     *
     * @return
     */
    public int getDir() {
        return dir;
    }

    /**
     * This will set the direction of the player (values from 1 to 8).
     *
     * @param i
     */
    public void setDir(int i) {
        dir = i;
    }

    /**
     * This will set a new grid.
     *
     * @param newGrid
     */
    public void setNewGrid(TileGrid newGrid) {
        grid = newGrid;
    }

    /**
     * This will draw the player after. If: check the bottom of the cylinder.
     * Else if: check the top of the cylinder. else: the middle of the cylinder.
     */
    public void Draw() {
        if (y > GRIDYpix - HALFHEIGHT) {
            var = y - GRIDYpix + HEIGHT;
        } else if (y < HALFHEIGHT) {
            var = y;
        } else {
            var = HALFHEIGHT;
        }
        DrawQuadTex(texture, HALFWIDTH, var, width, height);
    }
}
