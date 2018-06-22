package arkanoid.controller.actions;

import arkanoid.Gameplay;

public class RightKeyPressedAction extends Action{

    public RightKeyPressedAction(Gameplay gameplay){
        this.gameplay = gameplay;
    }

    /**
     * Reacts to pressing right arrow key
     */
    @Override
    public void perform() {
        gameplay.getPlayersPad().move(gameplay.getSpeed());
    }
}
