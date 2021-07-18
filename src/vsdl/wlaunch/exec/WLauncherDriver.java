package vsdl.wlaunch.exec;

import vsdl.omnigui.image.context.ImageContext;
import vsdl.omnigui.image.context.ImageContextProfileBuilder;
import vsdl.omnigui.image.source.SimpleMenuImageSource;
import vsdl.wlaunch.ui.Terminal;

import java.awt.*;
import java.awt.event.KeyEvent;

import static vsdl.wlaunch.exec.WLauncherEntityManager.*;

public class WLauncherDriver {

    //todo - all special screens under a special controller
    // builder classes for custom input sources

    private static void establishConnectionToRemoteHost() {
        try {
            getLinkSessionManager();
            System.out.println("Connected successfully!");
            //todo - next step once connected!
        } catch (IllegalStateException e) {
            SimpleMenuImageSource connectionFailureMenu = new SimpleMenuImageSource(
                    "Unable to contact game server!",
                    new String[]{"Retry", "Enter host-name", "Exit"},
                    new String[]{
                            "Continue attempting to contact the server",
                            "Manually enter a game server host-name",
                            "Exit the game client"
                    },
                    new boolean[]{true, false, true},
                    new Runnable[]{
                            WLauncherDriver::establishConnectionToRemoteHost,
                            () -> {}, //todo - a prompt to update WLauncherEntityManager.HOST
                            () -> System.exit(0)
                    },
                    () -> System.exit(0),
                    36,
                    Color.BLACK,
                    Color.RED,
                    Color.GREEN,
                    Color.WHITE,
                    Color.WHITE,
                    Color.GRAY
            );
            getTerminal().setImageContextProfile(
                    ImageContextProfileBuilder.start().appendImageContext(
                            new ImageContext(
                                    connectionFailureMenu,
                                    connectionFailureMenu.getOrigin(Terminal.CANVAS_DIM)
                            ),
                            KeyEvent.VK_ENTER,
                            KeyEvent.VK_UP,
                            KeyEvent.VK_DOWN,
                            KeyEvent.VK_NUMPAD8,
                            KeyEvent.VK_NUMPAD2,
                            KeyEvent.VK_ESCAPE
                    ).build()
            );
        }
    }

    public static void main(String[] args) {
        getTerminal().setImageContextProfile(ImageContextProfileBuilder.start().build());
        getTerminal().start();
        establishConnectionToRemoteHost();
    }
}
