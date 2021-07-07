package vsdl.wlaunch.ui.in;

import java.awt.*;
import java.awt.event.KeyEvent;

public class BaseInputMode extends InputMode {
    @Override
    public void mousePressedLeftAt(Point p) {
        System.out.println("Left click occurred at " + p);
    }

    @Override
    public void mousePressedMiddleAt(Point p) {
        System.out.println("Middle click occurred at " + p);
    }

    @Override
    public void mousePressedRightAt(Point p) {
        System.out.println("Right click occurred at " + p);

    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("Key pressed.");
    }
}
