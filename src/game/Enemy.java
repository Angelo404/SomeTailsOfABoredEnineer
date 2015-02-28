/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import static game.TileGrid.GRIDX;
import static game.TileGrid.GRIDXpix;
import static game.TileGrid.GRIDY;
import static game.TileGrid.GRIDYpix;
import static game.TileGrid.HALFSCREENXpix;
import static game.TileGrid.HALFSCREENYpix;
import static game.TileGrid.TILESIZE;
import org.newdawn.slick.opengl.Texture;
import static helper.Artist.*;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Euaggelos
 */
public class Enemy {

    private int width, height, health;
    private float x, y;
    private Texture texture;
    private final int Xmin = 0, Xmax = GRIDX*TILESIZE, Ymin = 0, Ymax = GRIDY*TILESIZE;
    private float varX = 0, varY = 0;
    
    /**
     * This will create the initial properties of the enemy.
     * @param texture The texture of the enemy.
     * @param width The width of the enemy (pixels).
     * @param height The height of the enemy (pixels).
     * 
     */
    public Enemy(Texture texture, int width, int height) {
        Random rnd = new Random();

        this.texture = texture;
        this.x = rnd.nextInt(Xmax - Xmin + 1) + Xmin;
        this.y = rnd.nextInt(Ymax - Ymin + 1) + Ymin;
        this.width = width;
        this.height = height;
    }

    /**
     * Getter for the initial X position of the enemy (pixels).
     * @return x
     */
    public float getX() {
        return x;
    }

    /**
     * Getter for the initial Y position of the enemy (pixels).
     * @return y
     */
    public float getY() {
        return y;
    }

    /**
     * This will draw the enemy.
     * If: checks if the player is at the middle of the screen (Y axis).
     * Else if: checks if the player is at the top half of the screen (Y axis).
     * Else: checks if the player is at the bottom half of the screen (Y axis).
     * ---
     * If: check if the player crosses the start of the cylinder on the left (X axis).
     * Else if: checks if the player crosses the end of the cylinder on the right (X axis).
     * @param PlayerX The X position of the player (pixels).
     * @param PlayerY The Y position of the player (pixels).
     */
    public void Draw(float PlayerX, float PlayerY) {
        if (PlayerY > HALFSCREENYpix && PlayerY < GRIDYpix-HALFHEIGHT){
            varY = y-PlayerY+HALFSCREENYpix;
        }
        else if (PlayerY <= HALFSCREENYpix){
            varY = y;
        }
        else
            varY = y - HEIGHT;
        
        varX = x - PlayerX - HALFSCREENXpix;
        if (varX < 0)
            varX = GRIDXpix + varX;
        else if(varX > GRIDXpix)
            varX = varX - GRIDXpix;   
                
        DrawQuadTex(texture, varX, varY, width, height);
            
    }

    /**
     * This will generate the enemies, pass them into an array and then return the array.
     * @param enemies
     * @return
     */
    public ArrayList<Enemy> generateEnemies(ArrayList<Enemy> enemies) {
        for (int i = 0; i < 30; ++i) {
            Enemy e = new Enemy(LoadTexture("enemy_32"), 32, 32);
            enemies.add(e);
        }
        return enemies;
    }

    /**
     * This will destroy all the enemies in the array and return the array. 
     * @param enemies
     * @return
     */
    public ArrayList<Enemy> deleteEnemies(ArrayList<Enemy> enemies) {
        for (int i = 0; i < 30; ++i) {
            enemies.remove(i);
        }
        return enemies;
    }
}
