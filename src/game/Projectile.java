package game;

import static game.TileGrid.GRIDXpix;
import static game.TileGrid.GRIDYpix;
import static game.TileGrid.HALFSCREENXpix;
import static game.TileGrid.HALFSCREENYpix;
import static helper.Artist.DrawQuadTex;
import static helper.Artist.HALFHEIGHT;
import static helper.Artist.HEIGHT;
import static java.lang.Math.sqrt;
import org.newdawn.slick.opengl.Texture;

public class Projectile {

    private final int width, height;
    private final float speed, halfSpeed, OriginX, OriginY;
    private float x, y;
    private final Texture texture;
    private final double dirX, dirY;
    private float varY, varX;

    public Projectile(Texture texture, float x, float y, int width, int height, float speed, float halfSpeed, float mousex, float mousey) {
        this.texture = texture;
        this.x = OriginX = x;
        this.y = OriginY = y;
        this.width = width;
        this.height = height;
        this.speed = speed * 2;
        this.halfSpeed = halfSpeed * 2;
        dirX = (mousex - x) / sqrt((mousey - y) * (mousey - y) + (mousex - x) * (mousex - x));
        dirY = (mousey - y) / sqrt((mousey - y) * (mousey - y) + (mousex - x) * (mousex - x));
    }

    /**
     * This will update the position of the projectile.
     */
    public void Update() {
        y += speed * dirY;
        x += speed * dirX;
    }

    /**
     * This will calculate the distance between the creation point and the
     * current one and return false in case of exceeding it.
     *
     * @param CurrentX Current X position of the projectile.
     * @param CurrentY Current Y position of the projectile.
     * @return True (delete the projectile) or false (do not delete the
     * projectile).
     */
    public boolean DeltaDist(float CurrentX, float CurrentY) {
        return (sqrt((CurrentX - OriginX) * (CurrentX - OriginX) + (CurrentY - OriginY) * (CurrentY - OriginY)) > 480);
    }

    /**
     * This will return the current X of the projectile.
     *
     * @return x
     */
    public float getX() {
        return x;
    }

    /**
     * This will return the current Y of the projectile.
     *
     * @return y
     */
    public float getY() {
        return y;
    }

    /**
     * This will draw the projectile on the screen. If: Checks if the player is
     * in the middle of the screen (Y axis). Else if: Checks if the player on
     * the top half of the screen (Y axis). Else: If the player is on the bottom
     * half of the screen. --- If: Checks if the player passes the left side of
     * the cylinder (X axis). Else if: Checks if the player passes the right
     * side of the cylinder (X axis).
     *
     * @param PlayerX Player's X position.
     * @param PlayerY Player's Y position.
     */
    public void Draw(float PlayerX, float PlayerY) {
        if (PlayerY > HALFSCREENYpix && PlayerY < GRIDYpix - HALFHEIGHT) {
            varY = y - PlayerY + HALFSCREENYpix;
        } else if (PlayerY <= HALFSCREENYpix) {
            varY = y;
        } else {
            varY = y - HEIGHT;
        }

        varX = x - PlayerX + HALFSCREENXpix;
        if (varX < 0) {
            varX = GRIDXpix + varX;
        } else if (varX > GRIDXpix) {
            varX = varX - GRIDXpix;
        }
        DrawQuadTex(texture, varX, varY, width, height);
    }
}
