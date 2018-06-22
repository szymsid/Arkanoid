package arkanoid;

import java.awt.*;

public class PlayersPad {

    public static int standardPlayersPadWidth = 100;
    public static int standardPlayersPadHeight = 10;
    private Rectangle hitBox;
    private Gameplay gameInstance;

    /**
     * Player constructor
     * @param game game instance
     * @param x starting X possition
     * @param y starting Y possition
     * @param width starting width
     * @param height starting height
     */
    PlayersPad(Gameplay game, int x, int y, int width, int height){
        hitBox = new Rectangle(x,y,width,height);
        gameInstance = game;
    }

    public int getX(){
        return hitBox.x;
    }

    public int getWidth(){
        return hitBox.width;
    }

    public int getHeight(){
        return hitBox.height;
    }

    public Rectangle getHitBox() { return hitBox; }

    public void  setWidth(int width) { hitBox.width = width; }

    public void setX(int x) {
        hitBox.x=x;
    }

    public void setY(int y) {
        hitBox.y=y;
    }

    /**
     * Moves pad on X axis
     * @param speed number of pixels pad moves by one KeyEvent
     */

    public void move(int speed)
    {
        hitBox.x += speed;
        if(hitBox.x <= 0) hitBox.x = 0;
        if(hitBox.x >= gameInstance.getGamefield().width - hitBox.width) hitBox.x = gameInstance.getGamefield().width - hitBox.width;
    }

    /**
     * Renders PlayersPad
     * @param g JPanel param
     */

    public void render(Graphics g){

        g.setColor(new Color(200,20,220));
        g.fillRect(hitBox.x,hitBox.y,hitBox.width,hitBox.height);

    }
};
