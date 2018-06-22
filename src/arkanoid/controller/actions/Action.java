package arkanoid.controller.actions;

import arkanoid.Gameplay;

public abstract class Action {
    /**
     * Abstract parent class for classes performing actions on logical or/and graphical models
     */
    protected Gameplay gameplay;
    public abstract void perform();
}
