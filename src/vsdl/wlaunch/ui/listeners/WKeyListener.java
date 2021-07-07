package vsdl.wlaunch.ui.listeners;

import vsdl.wlaunch.exec.WLauncherEntityManager;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class WKeyListener implements KeyListener {
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        WLauncherEntityManager.getInputModeStack().peek().keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
