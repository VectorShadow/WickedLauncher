package vsdl.wlaunch.ui.in;

import java.awt.*;
import java.awt.event.KeyEvent;

public abstract class InputMode {

    public abstract void mousePressedLeftAt(Point p);

    public abstract void mousePressedMiddleAt(Point p);

    public abstract void mousePressedRightAt(Point p);

    public abstract void keyPressed(KeyEvent e);
}
