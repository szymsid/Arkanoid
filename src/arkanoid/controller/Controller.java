package arkanoid.controller;

import arkanoid.GameInterface;
import arkanoid.Gameplay;
import arkanoid.controller.actions.*;
import arkanoid.view.events.*;

import java.util.HashMap;
import java.util.concurrent.BlockingQueue;

public class Controller implements Runnable {

    private Gameplay gameplay;
    private GameInterface gameInterface;
    private BlockingQueue<Event> blockingQueue;
    private HashMap<Class <? extends Event>, Action> actionMap;

    /**
     * Controller reacts to events by performing certain actions
     * @param gameplay logical unit
     * @param gameInterface graphical unit
     * @param blockingQueue event queue
     */

    public Controller(Gameplay gameplay, GameInterface gameInterface, final BlockingQueue<Event> blockingQueue) {
        this.gameplay = gameplay;
        this.gameInterface = gameInterface;
        this.blockingQueue = blockingQueue;
        actionMap = new HashMap<>();
        createMapping();
    }

    /**
     * Creates map assignig actions to specific event
     */
    private void createMapping() {
        actionMap.put(LeftKeyPressedEvent.class, new LeftKeyPressedAction(gameplay));
        actionMap.put(RightKeyPressedEvent.class, new RightKeyPressedAction(gameplay));
        actionMap.put(SpaceKeyPressedEvent.class, new SpaceKeyPressedAction(gameplay));
        actionMap.put(UpdateEvent.class, new UpdateAction(gameplay,gameInterface));
    }

    /**
     * Runnable loop
     */

    public void run() {
        while(true) {
            try {
                Event event = blockingQueue.take();
                blockingQueue.remove(event);
                actionMap.get(event.getClass()).perform();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
