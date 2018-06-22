package arkanoid;

import javafx.scene.layout.HBox;

import java.awt.*;
import java.util.Random;

public class Ball {

    public static int standardBallRadius = 8;
    private Gameplay gameInstance;
    private Point possition;
    private int radius;
    private Dimension movement = new Dimension(10,10);
    private Random rand;

    /**
     * Ball constructor
     * @param game game instance
     * @param x starting X possition
     * @param y starting Y possition
     * @param r ball radius
     */
    Ball(Gameplay game, int x, int y, int r){
        gameInstance = game;
        possition = new Point(x,y);
        radius = r;
        rand = new Random();
    }

    public void setPossition(int x, int y){
        possition.x = x;
        possition.y = y;
    }
    public void setMovement(int xMove,  int yMove){
        movement.width = xMove;
        movement.height = yMove;
    }

    public Dimension getMovement(){ return movement; }

    /**
     * Updates ball possition
     */

    public void update(){

        int value = movement.width;
        if(value < 0) value = - value;

        for(int i=1;i<=value;i++) {
            if (possition.x - 2 * radius <= 0 && movement.width < 0) movement.width = -movement.width;
            if (possition.x + 2 * radius >= gameInstance.getGamefield().width && movement.width > 0) movement.width = -movement.width;
            if (possition.y - 2 * radius <= 0 && movement.height < 0) movement.height = -movement.height;
            if (possition.y + 2 * radius >= gameInstance.getGamefield().height && movement.height > 0)
                gameInstance.loseBall();

            checkPlayerCollision();
            checkObstacleCollision();

            if (gameInstance.getObstacle().getTotalBricks() == 0)
                gameInstance.setIsWon(true);
            possition.move(possition.x + (movement.width / value), possition.y + (movement.height / value));
        }
    }

    /**
     * Checks if there is a collision between ball and player
     */

    private void checkPlayerCollision(){
        if(possition.x + 2*radius >= gameInstance.getPlayersPad().getX() && possition.y + 2*radius >= gameInstance.getGamefield().height - gameInstance.getPlayersPad().getHeight() && possition.x <= gameInstance.getPlayersPad().getX() +gameInstance.getPlayersPad().getWidth())
            movement.height = -movement.height;
    }

    /**
     * Checks if there is collision between ball and obstacles
     */

    private void checkObstacleCollision(){
        Rectangle hBox[][] = gameInstance.getObstacle().getHitBox();
        int ballX = possition.x;
        int ballY = possition.y;
        int fixedBallX = possition.x + 2*radius;
        int fixedBallY = possition.x + 2*radius;
        int obstacleX;
        int obstacleY;
        int fixedObstacleX;
        int fixedObstacleY;
        for(int i=0; i<hBox.length; i++)
        {
            for(int j=0;j<hBox[0].length;j++)
            {
                if(/*hBox[i][j].intersects(new Rectangle(possition.x, possition.y, 2*radius, 2*radius))*/)
                {
                    if(gameInstance.getObstacle().getLives()[i][j]!=Obstacle.LIVES.NULL) {

                        obstacleDestroy(i, j);
                        if(!gameInstance.isTransparent()) {
                            obstacleX = hBox[i][j].x;
                            obstacleY = hBox[i][j].y;
                            fixedObstacleX = obstacleX + hBox[i][j].width;
                            fixedObstacleY = obstacleY + hBox[i][j].height;

                            if(movement.width > 0 && movement.height >0){
                                if(fixedBallX >= obstacleX && ballX <= fixedObstacleX && ballY < obstacleY && fixedBallY >= obstacleY){
                                    movement.height = -movement.height;
                                    return;
                                }
                                if(ballY <= fixedObstacleY && fixedBallY >= obstacleY && ballX < obstacleX && fixedBallX >= obstacleX){
                                    movement.width = -movement.width;
                                    return;
                                }

                            }

                            if(movement.width < 0 && movement.height > 0){
                                if(fixedBallX >= obstacleX && ballX <= fixedObstacleX && ballY < obstacleY && fixedBallY >= obstacleY){
                                    movement.height = -movement.height;
                                    return;
                                }
                                if(ballY <= fixedObstacleY && fixedBallY >= obstacleY && ballX <= fixedObstacleX && fixedBallX > fixedObstacleX){
                                    movement.width = -movement.width;
                                    return;
                                }

                            }

                            if(movement.width < 0 && movement.height < 0){
                                if(ballX <= fixedObstacleX && fixedBallX >= obstacleX && ballY <= fixedObstacleY && fixedBallY > fixedObstacleY){
                                    movement.height = -movement.height;
                                    return;
                                }
                                if(ballY <= fixedObstacleY && fixedBallY >= obstacleY && ballX <= fixedObstacleX && fixedBallX > fixedObstacleX){
                                    movement.width = -movement.width;
                                    return;
                                }
                            }

                            if(movement.width > 0 && movement.height < 0){
                                if(ballY <= fixedObstacleY && fixedBallX >= obstacleX && ballX <= fixedObstacleX && fixedBallY > fixedObstacleY){
                                    movement.height = -movement.height;
                                    return;
                                }
                                if(ballY <= fixedObstacleY && fixedBallX >= obstacleX && ballX < obstacleX && fixedBallY >= obstacleY){
                                    movement.width = -movement.width;
                                    return;
                                }
                            }

                        }
                    }
                }
            }
        }
    }

    private void obstacleDestroy(int i, int j){
        gameInstance.setScore(gameInstance.getObstacle().getLives()[i][j].getPoints());
        gameInstance.getObstacle().crack(i, j);

        int r = rand.nextInt(20);
        if(r == 7) {
            gameInstance.getBonuses().add(new Bonus(gameInstance,possition.x, possition.y));
        }
    }

    /**
     * Renders ball
     * @param g JPanel param
     */

    public void render(Graphics g){

        g.setColor(new Color(0,0,0));
        g.fillOval(possition.x - radius, possition.y - radius, radius*2, radius*2);
    }


};
