package arkanoid.controller.actions;

import arkanoid.Gameplay;

public class SpaceKeyPressedAction extends Action{

    public SpaceKeyPressedAction(Gameplay gameplay){
        this.gameplay = gameplay;
    }

    /**
     * Reacts to pressing spacebar key
     */
    @Override
    public void perform() {
        gameplay.setIsInProgres(true);
    }
}
