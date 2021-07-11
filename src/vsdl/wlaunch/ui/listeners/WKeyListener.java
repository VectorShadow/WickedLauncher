package vsdl.wlaunch.ui.listeners;

import vsdl.wlaunch.exec.WLauncherEntityManager;
import vsdl.wlaunch.ui.Terminal;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class WKeyListener implements KeyListener {

    private final Terminal TERM;

    public WKeyListener(Terminal terminal) {
        TERM = terminal;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        TERM.getImageContextProfile().keyPress(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
