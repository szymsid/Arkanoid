package arkanoid.controller.actions;

import arkanoid.Gameplay;

public class LeftKeyPressedAction extends Action {

    public LeftKeyPressedAction(Gameplay gameplay){
        this.gameplay = gameplay;
    }

    /**
     * Reacts to pressing left arrow key
     */
    @Override
    public void perform() {
        gameplay.getPlayersPad().move(-gameplay.getSpeed());
    }
}
