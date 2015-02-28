package game;

import static game.TileGrid.GRIDYpix;
import static game.TileGrid.HALFSCREENXpix;
import static game.TileGrid.HALFSCREENYpix;
import static game.TileGrid.TILESIZE;
import static helper.Artist.*;
import helper.Maps;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.input.Keyboard;
import helper.Borders;
import java.awt.Font;
import java.io.IOException;
import static java.lang.Math.sqrt;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glRectf;
import org.newdawn.slick.Color;

import org.newdawn.slick.TrueTypeFont;

public class SomeTalesOfABoredEngineer {

    private final int speed = 5;
    private final float halfSpeed = (float) (speed / sqrt(2));
    private final int TEXTUREWIDTH = 32, TEXTUREHEIGHT = 32;
    private final int TOCENTER = TILESIZE / 2;

    private float mousex, mousey;
    private float PlayerX, PlayerY;
    private float BulletX, BulletY;
    private int mapLevel = 1;
    private TrueTypeFont font;
    private boolean antiAlias = true;
    private String s1, s2;
    private boolean gamePaused = false;
    public final Player p;
    public static int test[][];
    public int introCount = 1, creditsCount = 1;

    private static enum State {

        INTRO, GAME, MENU, CREDITS;
    }

    private State state = State.GAME;

    public SomeTalesOfABoredEngineer() throws IOException {

        BeginSession();

        Font awtFont = new Font("Times New Roman", Font.BOLD, 24);
        font = new TrueTypeFont(awtFont, antiAlias);

        test = Maps.LoadFromFile();
        System.out.println(test[1][1]);
        TileGrid grid = new TileGrid(test);
        Borders b = new Borders();
        p = new Player(LoadTexture("player_32"), TEXTUREWIDTH, TEXTUREHEIGHT, grid);
        Enemy e = new Enemy(LoadTexture("enemy_32"), TEXTUREWIDTH, TEXTUREHEIGHT);
        List<Enemy> enemies = new ArrayList<Enemy>();
        enemies = e.generateEnemies((ArrayList<Enemy>) enemies);
        List<Projectile> bullets = new ArrayList<Projectile>();

        Keyboard.enableRepeatEvents(true);

        while (!Display.isCloseRequested()) {

            keyboardInput();

            switch (state) {
                case INTRO:
                    glDisable(GL_TEXTURE_2D);
                    GL11.glColor3f(1.0f, 0.0f, 0.0f);
                    glRectf(0, 0, WIDTH, HEIGHT);
                    glEnable(GL_TEXTURE_2D);
                    font.drawString(100, HEIGHT + 10 + introCount, "In a galaxy far far away", Color.black);
                    font.drawString(100, HEIGHT + 35 + introCount, "The empire sends people, as slaves in a colony,", Color.black);
                    font.drawString(100, HEIGHT + 60 + introCount, "The colony is one of the moons of planet 34B of galaxy 007.", Color.black);
                    glColor3f(1, 1, 1);
                    introCount -= 1;
                    break;

                case CREDITS:
                    glDisable(GL_TEXTURE_2D);
                    GL11.glColor3f(1.0f, 0.0f, 0.0f);
                    glRectf(0, 0, WIDTH, HEIGHT);
                    glEnable(GL_TEXTURE_2D);
                    font.drawString(100, HEIGHT + 10 + creditsCount, "People invloved with this marvoulous project", Color.black);
                    font.drawString(100, HEIGHT + 35 + creditsCount, "Developers", Color.black);
                    font.drawString(100, HEIGHT + 60 + creditsCount, "Silver Raven", Color.black);
                    font.drawString(100, HEIGHT + 85 + creditsCount, "St0rmy", Color.black);
                    glColor3f(1, 1, 1);
                    creditsCount -= 1;
                    break;

                case GAME:
                    if (!gamePaused) {

                        PlayerX = p.getX();
                        PlayerY = p.getY();

                        grid.Draw(PlayerX, PlayerY);
                        p.Draw();

                        for (int w = 0; w < enemies.size(); ++w) {
                            Enemy tmpEnemy = enemies.get(w);
                            tmpEnemy.Draw(PlayerX, PlayerY);
                        }
                        for (int i = 0; i < bullets.size(); ++i) {
                            Projectile bullet = bullets.get(i);
                            bullet.Draw(PlayerX, PlayerY);
                            bullet.Update();
                            BulletX = bullet.getX();
                            BulletY = bullet.getY();
                            if (bullet.DeltaDist(BulletX, BulletY)) {
                                bullets.remove(i);
                                continue;
                            }
                            for (int q = 0; q < enemies.size(); ++q) {
                                Enemy tmpEnemy = enemies.get(q);
                                if (b.isHit(tmpEnemy.getX(), tmpEnemy.getY(), BulletX, BulletY)) {
                                    bullets.remove(i);
                                    enemies.remove(q);
                                }
                            }
                        }
                        mousex = Mouse.getX() + PlayerX - HALFSCREENXpix - TOCENTER;

                        if (PlayerY < HALFSCREENYpix) {
                            mousey = HEIGHT - Mouse.getY() - TOCENTER;
                        } else if (PlayerY > GRIDYpix - HALFSCREENYpix) {
                            mousey = GRIDYpix - Mouse.getY() - TOCENTER;
                        } else {
                            mousey = PlayerY + HALFSCREENYpix - Mouse.getY() - TOCENTER;
                        }

                        if (Mouse.isButtonDown(0)) {
                            Projectile bullet = new Projectile(LoadTexture("projectile_32"), PlayerX, PlayerY, TEXTUREWIDTH, TEXTUREHEIGHT, speed, halfSpeed, mousex, mousey);
                            bullets.add(bullet);
                        }

                    }
                    break;
                case MENU:
                    DrawMenu(LoadTexture("menu"));
                    //gamePaused = true;
                    break;
            }

            Display.update();
            Display.sync(60);
        }

        Display.destroy();
    }

    private void keyboardInput() {
        while (Keyboard.next()) {
            if (Keyboard.getEventKeyState()) {
                switch (Keyboard.getEventKey()) {
                    case Keyboard.KEY_P:
                        gamePaused = !gamePaused;
                        System.out.println(gamePaused);
                        break;
                    case Keyboard.KEY_ESCAPE:
                        introCount = creditsCount = 1;
                        if (state == State.INTRO) {
                            state = State.MENU;
                        } else if (state == State.MENU) {
                            state = State.GAME;
                        } else if (state == State.GAME) {
                            state = State.CREDITS;
                        } else if (state == State.CREDITS) {
                            state = State.INTRO;
                        }
                        break;
                    default:
                        continue;
                }
            }
        }
        if (!gamePaused) {
            if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
                if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
                    p.setDir(7);
                    p.xAxis(-halfSpeed);
                    p.yAxis(-halfSpeed);
                } else if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
                    p.setDir(5);
                    p.xAxis(-halfSpeed);
                    p.yAxis(halfSpeed);
                } else {
                    p.setDir(6);
                    p.xAxis(-speed);
                }
            } else if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
                if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
                    p.setDir(1);
                    p.xAxis(halfSpeed);
                    p.yAxis(-halfSpeed);
                } else if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
                    p.setDir(3);
                    p.xAxis(halfSpeed);
                    p.yAxis(halfSpeed);
                } else {
                    p.setDir(2);
                    p.xAxis(speed);
                }
            } else if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
                p.setDir(8);
                p.yAxis(-speed);
            } else if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
                p.setDir(4);
                p.yAxis(speed);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new SomeTalesOfABoredEngineer();
    }

}
