package InAndOut;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class IAndO extends JFrame implements KeyListener, MouseListener {

    private IKernelFromInOut kernel;

    /**
     * Creates In and Out
     * @param kernel needs a kernel to be built
     */
    public IAndO (IKernelFromInOut kernel){
        this.kernel = kernel;
        addKeyListener(this);
        addMouseListener(this);
    }


    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * tells when a key is pressed
     * @param e the event to be processed
     */
    @Override
    public void keyPressed(KeyEvent e) {
        kernel.keyPressed(e);
    }

    /**
     * Tells when a key is released
     * @param e the event to be processed
     */
    @Override
    public void keyReleased(KeyEvent e) {
    }

    /**
     * tells when a there is a click on the mouse
     * @param e the event to be processed
     */
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    /**
     * tells when the a mouse click is released
     * @param e the event to be processed
     */
    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}