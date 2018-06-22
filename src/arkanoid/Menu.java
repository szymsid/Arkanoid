package arkanoid;

import arkanoid.controller.Controller;
import arkanoid.view.events.*;
import arkanoid.view.events.Event;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Szymon Sidoruk
 * PROZ
 * Arkanoid
 */


public class Menu extends JPanel{

    private class SpaceKeyListener implements KeyListener {
        BlockingQueue<Event> queue;
        public SpaceKeyListener(BlockingQueue<Event> queue) {
            this.queue = queue;
        }

        @Override
        public void keyTyped(java.awt.event.KeyEvent e) {

        }

        @Override
        public void keyPressed(java.awt.event.KeyEvent e) {
            if(e.getKeyCode() == java.awt.event.KeyEvent.VK_SPACE){
                try {
                    queue.put(new SpaceKeyPressedEvent());
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
            if(e.getKeyCode() == java.awt.event.KeyEvent.VK_RIGHT) {
                try {
                    queue.put(new RightKeyPressedEvent());
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
            if(e.getKeyCode() == java.awt.event.KeyEvent.VK_LEFT){
                try {
                    queue.put(new LeftKeyPressedEvent());
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }

        }

        @Override
        public void keyReleased(java.awt.event.KeyEvent e) {

        }
    }

    private static JFrame frame;
    private static Gameplay game;
    private static GameInterface gameInterface;
    private static BlockingQueue<Event> queue;
    Menu() {

        frame = new JFrame("Arkanoid");
        frame.setSize(800, 700);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
        game = new Gameplay(new Dimension(frame.getWidth()*3/4,frame.getHeight()));
        gameInterface = new GameInterface(game);
        gameInterface.setSize(frame.getSize());
        queue = new LinkedBlockingQueue<>();
        frame.add(gameInterface);
        frame.addKeyListener(new SpaceKeyListener(queue));
    }

    public static void main(String[] args) throws InterruptedException {
        new Menu();

        final double amountOfTicks = 30.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        Controller controller = new Controller(game,gameInterface,queue);
        Thread thread = new Thread(controller);
        thread.start();
        while(!game.isInProgress())
        {                                       //Problemem był brak instrukcji w pętli debugger przepuszczał, po wciśnięciu spacji, a kompilator nie.
            System.out.println();               //System.out.println();, jest jedyną instrukcją, ktróa działa zawsze,
        }                                       //okazjonalnie działało if(queue.size>0)break;, nie rozumiem dlaczego
        long lastTime = System.nanoTime();
        while(game.isInProgress()){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            if (delta >= 1) {
                delta--;
                queue.put(new UpdateEvent());
            }
            if(game.isWon()) break;
            if(game.isLost()) break;
        }

    }

};