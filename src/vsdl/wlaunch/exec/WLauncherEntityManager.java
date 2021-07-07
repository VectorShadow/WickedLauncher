package vsdl.wlaunch.exec;

import vsdl.datavector.link.DataLink;
import vsdl.omnigui.api.Gui;
import vsdl.wlaunch.connections.WLauncherDataMessageHandler;
import vsdl.wlaunch.ui.in.InputModeStack;
import vsdl.wlaunch.ui.listeners.WKeyListener;
import vsdl.wlaunch.ui.listeners.WMouseListener;
import vsdl.wlaunch.ui.listeners.WWindowListener;

import java.awt.*;
import java.io.IOException;
import java.net.Socket;

public class WLauncherEntityManager {

    public static final String HOST_NAME = "vps-46c76bb0.vps.ovh.ca";
    public static final int HOST_PORT = 31592;

    public static final Dimension CANVAS_DIM = new Dimension(1600, 1000);

    private static DataLink DATA_LINK = null;

    private static Gui GUI = null;

    private static InputModeStack IMS = null;

    public static DataLink getDataLink() throws IOException {
        if (DATA_LINK == null) {
            DATA_LINK = new DataLink(
                    new Socket(HOST_NAME, HOST_PORT),
                    new WLauncherDataMessageHandler()
            );
            DATA_LINK.start();
        }
        return DATA_LINK;
    }

    public static Gui getGui() {
        if (GUI == null) {
            GUI = new Gui(CANVAS_DIM);
            GUI.createFrame(false, "WickedLauncher", null);
            GUI.addEventListener(new WKeyListener());
            GUI.addEventListener(new WMouseListener());
            GUI.addEventListener(new WWindowListener());
        }
        return GUI;
    }

    public static InputModeStack getInputModeStack() {
        if (IMS == null) {
            IMS = new InputModeStack();
        }
        return IMS;
    }
}
