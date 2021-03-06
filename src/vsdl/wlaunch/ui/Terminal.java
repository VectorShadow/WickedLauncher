package vsdl.wlaunch.ui;

import vsdl.omnigui.api.Gui;
import vsdl.omnigui.image.context.ImageContextProfile;
import vsdl.wlaunch.ui.listeners.WKeyListener;
import vsdl.wlaunch.ui.listeners.WMouseListener;
import vsdl.wlaunch.ui.listeners.WWindowListener;

import java.awt.*;

public class Terminal extends Thread {

    public static final Dimension CANVAS_DIM = new Dimension(1600, 1000);
    public static final int REDRAW_INTERVAL = 1000 / 60;

    private final Gui GUI;
    private ImageContextProfile ICP;

    public Terminal() {
        GUI = new Gui(CANVAS_DIM);
        GUI.createFrame(false, "WickedLauncher", null);
        GUI.addEventListener(new WKeyListener(this));
        GUI.addEventListener(new WMouseListener(this));
        GUI.addEventListener(new WWindowListener());
    }

    public Gui getGui() {
        return GUI;
    }

    public ImageContextProfile getImageContextProfile() {
        return ICP;
    }

    public void setImageContextProfile(ImageContextProfile imageContextProfile) {
        ICP = imageContextProfile;
    }

    private void redraw() {
        GUI.clearCanvas();
        ICP.paint(GUI);
        GUI.updateFrameImage();
    }

    @Override
    public void run() {
        do {
            try {
                Thread.sleep(REDRAW_INTERVAL);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            redraw();
        } while(true);
    }
}
