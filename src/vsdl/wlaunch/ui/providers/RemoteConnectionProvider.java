package vsdl.wlaunch.ui.providers;

import vsdl.omnigui.image.context.ImageContext;
import vsdl.omnigui.image.context.ImageContextProfileBuilder;
import vsdl.omnigui.image.source.ScrollableMenuTextDialogImageSource;
import vsdl.wlaunch.ui.Terminal;

import java.awt.*;

import static vsdl.wlaunch.exec.WLauncherEntityManager.*;
import static java.awt.event.KeyEvent.*;

public class RemoteConnectionProvider {
    private static final ScrollableMenuTextDialogImageSource CNX_ERR_SRC =
            ScrollableMenuTextDialogImageSource.builder()
                    .setColors(
                            Color.BLACK,
                            Color.RED,
                            Color.LIGHT_GRAY,
                            Color.WHITE,
                            Color.DARK_GRAY,
                            Color.BLACK,
                            Color.BLACK,
                            Color.BLACK
                    )
                    .setTextHeight(36)
                    .setTitle("Unable to contact game server!")
                    .setOptionCount(3)
                    .setOptionNames(
                            "Retry",
                            "Enter alternate host name",
                            "Exit")
                    .setOptionDescriptions(
                            "Continue attempting to contact the server",
                            "Manually enter a game server host-name",
                            "Exit the game client"
                    )
                    .setOptionEnabledState(false, 1)
                    .setOptionExecutions(
                            RemoteConnectionProvider::establishConnectionToRemoteHost,
                            () -> {}, //todo - a prompt to update WLauncherEntityManager.HOST
                            () -> System.exit(0)
                    )
                    .setEscapeExecution(
                            () -> System.exit(0)
                    )
                    .build();

    private static final int[] HOTEYS = {
            VK_ENTER,
            VK_UP,
            VK_DOWN,
            VK_NUMPAD8,
            VK_NUMPAD2,
            VK_ESCAPE
    };

    public static void establishConnectionToRemoteHost() {
        try {
            getLinkSessionManager();
            LogonUserProvider.logonPrompt();
        } catch (IllegalStateException e) {
            getTerminal().setImageContextProfile(
                    ImageContextProfileBuilder
                            .start()
                            .appendImageContext(
                                    new ImageContext(
                                            CNX_ERR_SRC,
                                            Terminal.CANVAS_DIM
                                    ),
                                    HOTEYS
                            )
                            .build()
            );
        }
    }
}
