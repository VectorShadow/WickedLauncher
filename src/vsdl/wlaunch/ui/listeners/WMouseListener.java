package vsdl.wlaunch.ui.listeners;

import vsdl.omnigui.api.Gui;
import vsdl.wlaunch.exec.WLauncherEntityManager;
import vsdl.wlaunch.ui.Terminal;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class WMouseListener implements MouseListener {

    private final Terminal TERM;

    public WMouseListener(Terminal terminal) {
        TERM = terminal;
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        Point at = TERM.getGui().getMouseEventLocationOnCanvas(e);
        switch (e.getButton()) {
            case MouseEvent.BUTTON1:
                TERM.getImageContextProfile().mouseClick(at, true);
                break;
            case MouseEvent.BUTTON3:
                TERM.getImageContextProfile().mouseClick(at, false);
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
