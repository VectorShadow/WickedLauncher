package vsdl.wlaunch.ui.listeners;

import vsdl.wlaunch.exec.WLauncherEntityManager;
import vsdl.wlaunch.ui.in.InputMode;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class WMouseListener implements MouseListener {

    private InputMode getCurrentInputMode() {
        return WLauncherEntityManager.getInputModeStack().peek();
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        Point at = WLauncherEntityManager.getGui().getMouseEventLocationOnCanvas(e);
        switch (e.getButton()) {
            case MouseEvent.BUTTON1:
                getCurrentInputMode().mousePressedLeftAt(at);
                break;
            case MouseEvent.BUTTON2:
                getCurrentInputMode().mousePressedMiddleAt(at);
                break;
            case MouseEvent.BUTTON3:
                getCurrentInputMode().mousePressedRightAt(at);
                break;
            default: //nothing
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
