package arkanoid;

import java.util.Random;
import java.awt.*;

public class Bonus {

    private Rectangle hitBox;
    private TYPE type;
    private Gameplay gameInstance;
    public enum TYPE{
        LONGPAD(205,50,50),
        SHORTPAD(50,205,50),
        SLOWPAD(70,205,70),
        FASTPAD(123,123,13),
        SLOWBALL(213,231,13),
        FASTBALL(12,243,123),
        TRANSPARENTBALL(20,170,170);

        int red,green,blue;

        TYPE(int r, int g, int b){
            red = r;
            green = g;
            blue = b;
        }

        Color getColor(){
            return new Color(red,green,blue);
        }

    }

    /**
     * Bonus constructor
     * @param game game instance
     * @param x starting X possition
     * @param y starting Y possition
     */
    Bonus(Gameplay game, int x, int y){
        gameInstance = game;
        hitBox = new Rectangle(x,y,20,20);
        Random rand = new Random();
        int t = rand.nextInt(7);
        assignType(t);
    }

    public void update(){
        hitBox.y+=10;
    }

    public int getY(){
        return hitBox.y;
    }

    public Rectangle getHitBox(){
        return hitBox;
    }

    /**
     * Determines type of bonus
     * @param t int value of a type
     */
    public void assignType(int t){
       if(t == 0) type = TYPE.LONGPAD;
       if(t == 1) type = TYPE.SHORTPAD;
       if(t == 2) type = TYPE.FASTPAD;
       if(t == 3) type = TYPE.SLOWPAD;
       if(t == 4) type = TYPE.FASTBALL;
       if(t == 5) type = TYPE.SLOWBALL;
       if(t == 6) type = TYPE.TRANSPARENTBALL;
    }

    /**
     * Applies effect after collision with player
     */
    public void startEffect(){
        if (type == Bonus.TYPE.LONGPAD) {
            gameInstance.setIsLonger(true);
            gameInstance.setIsShorter(false);
            gameInstance.setLengthTimer(System.currentTimeMillis());
            gameInstance.getPlayersPad().setWidth(PlayersPad.standardPlayersPadWidth * 4 / 3);
        }
        if (type == Bonus.TYPE.SHORTPAD) {
            gameInstance.setIsLonger(false);
            gameInstance.setIsShorter(true);
            gameInstance.setLengthTimer(System.currentTimeMillis());
            gameInstance.getPlayersPad().setWidth(PlayersPad.standardPlayersPadWidth * 3 / 4);

        }
        if (type == Bonus.TYPE.FASTPAD) {
            gameInstance.setIsPadFaster(true);
            gameInstance.setIsPadSlower(false);
            gameInstance.setPadSpeedTimer(System.currentTimeMillis());
            gameInstance.setSpeed(Gameplay.standardSpeed + 5);
        }
        if (type == Bonus.TYPE.SLOWPAD) {
            gameInstance.setIsPadFaster(false);
            gameInstance.setIsPadSlower(true);
            gameInstance.setPadSpeedTimer(System.currentTimeMillis());
            gameInstance.setSpeed(Gameplay.standardSpeed - 5);

        }
        if (type == Bonus.TYPE.FASTBALL) {
            gameInstance.setIsBallFaster(true);
            gameInstance.setIsBallSlower(false);
            gameInstance.setBallSpeedTimer(System.currentTimeMillis());
            if (gameInstance.getBall().getMovement().getWidth() > 0)
                if (gameInstance.getBall().getMovement().getHeight() > 0)
                    gameInstance.getBall().setMovement(12, 12);
                else
                    gameInstance.getBall().setMovement(12, -12);
            else if (gameInstance.getBall().getMovement().getHeight() > 0)
                gameInstance.getBall().setMovement(-12, 12);
            else
                gameInstance.getBall().setMovement(-12, -12);

        }
        if (type == Bonus.TYPE.SLOWBALL) {
            gameInstance.setIsBallSlower(true);
            gameInstance.setIsBallFaster(false);
            gameInstance.setBallSpeedTimer(System.currentTimeMillis());
            if (gameInstance.getBall().getMovement().getWidth() > 0)
                if (gameInstance.getBall().getMovement().getHeight() > 0)
                    gameInstance.getBall().setMovement(8, 8);
                else
                    gameInstance.getBall().setMovement(8, -8);
            else if (gameInstance.getBall().getMovement().getHeight() > 0)
                gameInstance.getBall().setMovement(-8, 8);
            else
                gameInstance.getBall().setMovement(-8, -8);
        }
        if (type == Bonus.TYPE.TRANSPARENTBALL) {
            gameInstance.setIsTransparent(true);
            gameInstance.setTransparencyTimer(System.currentTimeMillis());
        }
    }

    /**
     * Renders bonus
     * @param g JPanel param
     */

    public void render(Graphics g){

        g.setColor(type.getColor());

        g.fillRect(hitBox.x, hitBox.y, hitBox.width, hitBox.height);
        g.setColor(new Color(0,0,0));
        g.drawRect(hitBox.x, hitBox.y, hitBox.width, hitBox.height);
    }
};
