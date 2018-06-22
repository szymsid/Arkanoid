package arkanoid;

import java.awt.*;
import java.util.Random;

public class Obstacle {
    private Rectangle hitBox[][];
    private Gameplay gameInstance;
    private int brickWidth;
    private int brickHeight;
    private int totalBricks;
    private LIVES lives[][];
    public enum LIVES{
        NULL(0),
        ONE(0,255,0,10),
        TWO(0,255,255,20),
        THREE(255,255,0,30),
        FOUR(120,0,255,40),
        FIVE(255,120,0,50);

        int red,green,blue,points;

        LIVES(int r, int g, int b, int p){
            red = r;
            green = g;
            blue = b;
            points = p;
        }
        LIVES(int p){
            points = p;
        }

        Color getColor(){
            return new Color(red,green,blue);
        }

        int getPoints(){
            return points;
        }
    }

    /**
     * Obstacle constructor
     * @param game game instance
     * @param row number of rows
     * @param col number of columns
     */

    Obstacle(Gameplay game, int row, int col){
        gameInstance = game;
        lives = new LIVES[row][col];
        hitBox = new Rectangle[row][col];
        Random rand = new Random();
        brickWidth = (gameInstance.getGamefield().width - 120) / col;
        brickHeight = ((gameInstance.getGamefield().height /3) - 50) / row;
        totalBricks = 0;
        int life;
        for(int i=0;i<lives.length;i++){
            for(int j=0;j<lives[0].length;j++)
            {
                life = rand.nextInt(6);
                totalBricks+=life;
                hitBox[i][j] = new Rectangle(60+j*brickWidth,50+i*brickHeight, brickWidth, brickHeight);
                assignLives(life,i,j);
            }
        }

    }

    /**
     * Determines durability of each obstacle element
     * @param l number of collisions to destroy
     * @param i element's row
     * @param j element's collumn
     */
    private void assignLives(int l,int i, int j){
        if(l==0)lives[i][j] = LIVES.NULL;
        if(l==1)lives[i][j] = LIVES.ONE;
        if(l==2)lives[i][j] = LIVES.TWO;
        if(l==3)lives[i][j] = LIVES.THREE;
        if(l==4)lives[i][j] = LIVES.FOUR;
        if(l==5)lives[i][j] = LIVES.FIVE;
    }

    public Rectangle[][] getHitBox(){
        return hitBox;
    }

    public LIVES[][] getLives(){
        return lives;
    }

    /**
     * Reaction for collision with ball
     * @param i row of collided element
     * @param j column of collided element
     */
    public void crack(int i, int j){
        totalBricks--;
        if(lives[i][j]==LIVES.ONE)lives[i][j]=LIVES.NULL;
        if(lives[i][j]==LIVES.TWO)lives[i][j]=LIVES.ONE;
        if(lives[i][j]==LIVES.THREE)lives[i][j]=LIVES.TWO;
        if(lives[i][j]==LIVES.FOUR)lives[i][j]=LIVES.THREE;
        if(lives[i][j]==LIVES.FIVE)lives[i][j]=LIVES.FOUR;
    }

    public int getTotalBricks(){
        return totalBricks;
    }

    /**
     * renders obstacles
     * @param g JPanel param
     */

    public void render(Graphics g){
        for(int i=0;i<lives.length;i++){
            for(int j=0;j<lives[0].length;j++) {
                if (lives[i][j] != LIVES.NULL) {
                    g.setColor(lives[i][j].getColor());

                    g.fillRect(hitBox[i][j].x, hitBox[i][j].y, hitBox[i][j].width, hitBox[i][j].height);
                    g.setColor(new Color(0, 0, 0));
                    g.drawRect(hitBox[i][j].x, hitBox[i][j].y, hitBox[i][j].width, hitBox[i][j].height);
                }
            }
        }
    }
};
