package helper;

import game.Tile;
import game.TileType;
import game.TileGrid;

public class Borders {

    int w, h;
    Tile t;
    TileType tt;
    TileGrid tg;

    public Borders() {
        this.w = Artist.WIDTH;
        this.h = Artist.HEIGHT;
    }

    public boolean isBorder(float posx, float posy) {
        return posx + 1 > w || posx - 1 < -5 || posy + 1 > h || posy + 1 < -5;
    }

    public boolean isLeftBorder(float posx) {
        return !(posx - 5 < 0);
    }

    public boolean isRightBorder(float posx) {
        return !(posx + 32 >= w);
    }

    public boolean isTopBorder(float posy) {
        return !(posy - 5 < 0);
    }

    public boolean isBottomBorder(float posy) {
        return !(posy + 38 >= h);
    }

    public boolean isHit(float enemyX, float enemyY, float bulletX, float bulletY) {
        float y = enemyY - bulletY;
        float x = enemyX - bulletX;
        double d;
        d = Math.sqrt(Math.pow(y, 2) + Math.pow(x, 2));
        return (d <= 32);
    }

}
