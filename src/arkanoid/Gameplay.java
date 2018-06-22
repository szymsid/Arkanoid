
package arkanoid;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Gameplay{

    private Dimension gameField;
    private PlayersPad playersPad;
    private Ball ball;
    private Obstacle obstacle;
    private List<Bonus> bonuses;
    private int score;
    private int lives;
    private int speed;
    public static int standardSpeed = 25;
    private double lengthTimer = 0;
    private double padSpeedTimer = 0;
    private double ballSpeedTimer = 0;
    private double transparencyTimer = 0;
    private static boolean isLost = false;
    private static boolean isWon = false;
    private static boolean isInProgress = false;
    private static boolean isLonger = false;
    private static boolean isShorter = false;
    private static boolean isPadSlower = false;
    private static boolean isPadFaster = false;
    private static boolean isBallSlower = false;
    private static boolean isBallFaster = false;
    private static boolean isTransparent = false;

    /**
     * Class responsible for logical engine of the game
     * @param gameField determines bounds of gamefield
     */

    public Gameplay(Dimension gameField) {

        this.gameField = gameField;
        speed = standardSpeed;
        playersPad = new PlayersPad(this, (int) ((gameField.getWidth() - PlayersPad.standardPlayersPadWidth) / 2), (int) (gameField.getHeight() - PlayersPad.standardPlayersPadHeight), PlayersPad.standardPlayersPadWidth, PlayersPad.standardPlayersPadHeight);
        ball = new Ball(this, (int) ((gameField.getWidth() - Ball.standardBallRadius) / 2), (int) (gameField.getHeight() - PlayersPad.standardPlayersPadHeight - 2 * (Ball.standardBallRadius) - 10), Ball.standardBallRadius);
        lives = 3;
        bonuses = new ArrayList<>();
        score = 0;
        ball.setMovement(10, -10);
    }

    /**
     * Method determining outcome of ball dropping out of bounds
     */

    public void loseBall() {
        ball.setPossition(((int) ((gameField.getWidth() - Ball.standardBallRadius) / 2)), (int) (gameField.getHeight() - PlayersPad.standardPlayersPadHeight - (Ball.standardBallRadius)) - 10);
        ball.setMovement(10, -10);
        isLonger = false;
        isShorter = false;
        isPadFaster = false;
        isPadSlower = false;
        isBallFaster = false;
        isBallSlower = false;
        isTransparent = false;
        lives--;
    }

    /**
     * Checks if LONGPAD bonus or SHORTPAD bonus has been obtained less than 8 seconds ago
     * @param lengthTimer time since acquiring bonus
     * @return time if less than 8s
     */
    public double checkLengthTimer(double lengthTimer){
        if (System.currentTimeMillis() - lengthTimer >= 8000 || (!isLonger && !isShorter)) {
            isLonger = false;
            isShorter = false;
            playersPad.setWidth(PlayersPad.standardPlayersPadWidth);
            return 0;
        }
        return lengthTimer;
    }

    /**
     * Checks if FASTPAD bonus or SLOWPAD bonus has been obtained less than 8 seconds ago
     * @param padSpeedTimer time since acquiring bonus
     * @return time if less than 8s
     */
    public double checkPadSpeedTimer(double padSpeedTimer){
        if (System.currentTimeMillis() - padSpeedTimer >= 8000 || (!isPadFaster && !isPadSlower)) {
            isPadSlower = false;
            isPadFaster = false;
            speed = standardSpeed;
            return 0;
        }

        return padSpeedTimer;
    }

    /**
     * Checks if FASTBALL bonus or SLOWBALL bonus has been obtained less than 8 seconds ago
     * @param ballSpeedTimer time since acquiring bonus
     * @return time if less than 8s
     */
    public double checkBallSpeedTimer(double ballSpeedTimer){
        if (System.currentTimeMillis() - ballSpeedTimer >= 8000 || (!isBallFaster && !isBallSlower)) {
            isBallFaster = false;
            isBallSlower = false;
            if (ball.getMovement().getWidth() > 0)
                if (ball.getMovement().getHeight() > 0)
                    ball.setMovement(10, 10);
                else
                    ball.setMovement(10, -10);
            else if (ball.getMovement().getHeight() > 0)
                ball.setMovement(-10, 10);
            else
                ball.setMovement(-10, -10);

            return 0;
        }
        return ballSpeedTimer;
    }

    /**
     * Checks if TRANSPARENTBALL bonus has been obtained less than 8 seconds ago
     * @param transparencyTimer time since acquiring bonus
     * @return time if less than 8s
     */
    public double checkTransparencyTimer(double transparencyTimer){
        if (System.currentTimeMillis() - transparencyTimer >= 8000 || !isTransparent) {
            isTransparent = false;
            return 0;
        }
        return transparencyTimer;
    }

    /**
     * Determines action when bonus contacts player
     */
    public void acquireBonus(){
        List<Bonus> toRemove = new ArrayList<>();
        for (Bonus b : bonuses) {
            if (b.getHitBox().intersects(playersPad.getHitBox())) {
                toRemove.add(b);
                b.startEffect();
            }
            b.update();
            if (b.getY() > gameField.height)
                toRemove.add(b);
        }

        bonuses.removeAll(toRemove);
        toRemove.clear();
    }

    /**
     * Updates logical values
     */
    public void update(){
        ball.update();
        acquireBonus();
        if(lives ==0){
            isLost = true;
            ball.setMovement(0,0);
        }
        lengthTimer = checkLengthTimer(lengthTimer);
        padSpeedTimer = checkPadSpeedTimer(padSpeedTimer);
        ballSpeedTimer = checkBallSpeedTimer(ballSpeedTimer);
        transparencyTimer = checkTransparencyTimer(transparencyTimer);
    }


    public PlayersPad getPlayersPad() {
        return this.playersPad;
    }

    public Ball getBall(){
        return  this.ball;
    }

    public Dimension getGamefield() {
        return this.gameField;
    }

    public Obstacle getObstacle() {
        return this.obstacle;
    }

    public List<Bonus> getBonuses() {
        return this.bonuses;
    }

    public boolean isInProgress() {
        return isInProgress;
    }

    public boolean isLost() {
        return isLost;
    }

    public int getLives() {
        return lives;
    }

    public int getScore() {
        return score;
    }

    public int getSpeed() { return speed;}

    public boolean isTransparent() {
        return isTransparent;
    }

    public boolean isWon() { return isWon; }

    public void setIsWon(boolean b) {
        isWon = b;
    }

    public void setGameField(Dimension gameField) {
        this.gameField = gameField;
    }

    public void setObstacle(Obstacle obstacle){
        this.obstacle = obstacle;
    }

    public void setScore(int s) {
        score += s;
    }

    public void setSpeed(int s) {
        speed = s;
    }

    public void setIsInProgres(boolean b) { isInProgress = b; }

    public void setIsLonger(boolean flag) {
        isLonger = flag;
    }

    public void setIsShorter(boolean flag) {
        isShorter = flag;
    }

    public void setIsPadFaster(boolean flag) {
        isPadFaster = flag;
    }

    public void setIsPadSlower(boolean flag) {
        isPadSlower= flag;
    }

    public void setIsBallFaster(boolean flag) {
        isBallFaster = flag;
    }

    public void setIsBallSlower(boolean flag) {
        isBallSlower = flag;
    }

    public void setIsTransparent(boolean flag) { isTransparent = flag; }

    public void setLengthTimer(double time){
        lengthTimer = time;
    }

    public void setPadSpeedTimer(double time){
        padSpeedTimer = time;
    }

    public void setBallSpeedTimer(double time){
        ballSpeedTimer = time;
    }

    public  void setTransparencyTimer(double time){
        transparencyTimer = time;
    }

};
