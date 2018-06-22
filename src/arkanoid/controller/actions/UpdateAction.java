package arkanoid.controller.actions;

import arkanoid.GameInterface;
import arkanoid.Gameplay;

public class UpdateAction extends Action {

    private GameInterface gameInterface;

    /**
     * Class performing update on logical and graphical unit
     * @param gameplay logical unit
     * @param gameInterface graphical unit
     */
    public UpdateAction(final Gameplay gameplay, final GameInterface gameInterface) {
        this.gameplay = gameplay;
        this.gameInterface = gameInterface;
    }

    public void perform() {
        gameplay.update();
        gameInterface.repaint();
    }
}