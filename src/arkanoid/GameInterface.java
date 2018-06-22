package arkanoid;

import javax.swing.*;
import java.awt.*;

public class GameInterface extends JPanel {

    private Gameplay gameInstance;

    GameInterface(Gameplay game){
        gameInstance = game;
    }

    /**
     * Sets standard size
     * @param size gameField size
     */
    public void setSize(Dimension size) {
        super.setSize(size);
        if (!gameInstance.isInProgress()) {
            Dimension gameField = new Dimension(size.width * 3 / 4, size.height * 95 / 100);
            gameInstance.setGameField(gameField);
            gameInstance.setObstacle(new Obstacle(gameInstance, 8, 15));
            gameInstance.getBall().setPossition(((int) ((gameField.getWidth() - Ball.standardBallRadius) / 2)), (int) (gameField.getHeight() - PlayersPad.standardPlayersPadHeight - (Ball.standardBallRadius)) - 10);
            gameInstance.getPlayersPad().setX((int) ((gameField.getWidth() - PlayersPad.standardPlayersPadWidth) / 2));
            gameInstance.getPlayersPad().setY((int) (gameField.getHeight() - PlayersPad.standardPlayersPadHeight));
        }
    }

    /**
     * Paints graphical unit
     * @param g JPane param
     */
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(new Color(0, 0, 255));
        g.fillRect(0, 0, gameInstance.getGamefield().width, gameInstance.getGamefield().height);
        g.setColor(new Color(0, 0, 0));
        g.drawRect(0, 0, gameInstance.getGamefield().width, gameInstance.getGamefield().height);
        g.setColor(new Color(255, 0, 0));
        g.fillRect(gameInstance.getGamefield().width, 0, getWidth() - gameInstance.getGamefield().width, gameInstance.getGamefield().height);
        g.setColor(new Color(0, 0, 0));
        g.drawRect(gameInstance.getGamefield().width, 0, getWidth() - gameInstance.getGamefield().width, gameInstance.getGamefield().height);

        if (gameInstance.isLost()) {
            g.setFont(new Font("arial", Font.BOLD, 50));
            g.drawString("YOU LOST", gameInstance.getGamefield().width / 2 - 120, gameInstance.getGamefield().height / 2);
        }
        if (gameInstance.isWon()) {
            g.setFont(new Font("arial", Font.BOLD, 50));
            g.drawString("YOU WON", gameInstance.getGamefield().width / 2 - 100, gameInstance.getGamefield().height / 2);
        }

        gameInstance.getObstacle().render(g);
        gameInstance.getPlayersPad().render(g);
        gameInstance.getBall().render(g);

        for (Bonus b : gameInstance.getBonuses()) {
            b.render(g);
        }

        g.setColor(new Color(0,0,0));
        for(int j = 0; j < gameInstance.getLives(); j++)
            g.fillOval(gameInstance.getGamefield().width + 20 + j*10,30,8,8);
        g.setFont(new Font("arial", Font.BOLD,20));
        g.drawString("SCORE: " + gameInstance.getScore(),gameInstance.getGamefield().width + 20, 80);
        int i=0;
        g.setFont(new Font("arial", Font.BOLD, 12));
        for(Bonus.TYPE type: Bonus.TYPE.values()){
            g.drawRect(gameInstance.getGamefield().width + 20, 100 + i*20, 15, 15);
            g.setColor(type.getColor());
            g.fillRect(gameInstance.getGamefield().width + 20, 100 + i*20, 15, 15);
            g.drawString(type.name(),gameInstance.getGamefield().width + 50, 110 + i*20);
            i++;
        }
    }


}
