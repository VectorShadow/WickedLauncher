package vsdl.wlaunch.exec;

import vsdl.omnigui.image.context.ImageContext;
import vsdl.omnigui.image.context.ImageContextProfileBuilder;
import vsdl.omnigui.image.source.FieldEntryTextDialogImageSource;
import vsdl.omnigui.image.source.InteractiveTextDialogImageSource;
import vsdl.omnigui.image.source.ScrollableMenuTextDialogImageSource;
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
            FieldEntryTextDialogImageSource userNameAndPasswordInputMenu =
                    FieldEntryTextDialogImageSource.builder()
                    .setColors(
                            Color.BLACK,
                            Color.RED,
                            Color.LIGHT_GRAY,
                            Color.WHITE,
                            Color.BLACK,
                            Color.BLACK,
                            Color.BLUE,
                            Color.WHITE
                    )
                    .setTextHeight(36)
                    .setTitle("Enter Username and Password:")
                            .setOptionCount(3)
                            .setOptionNames(
                                    "Username: ",
                                    "Password: ",
                                    "Confirm: "
                            )
                            .setInputFieldMaskState(true, 1)
                            .setInputFieldMaskState(true, 2)
                            .setInputFieldCharacterLimits(
                                    8,
                                    8,
                                    8
                            )
                    .setEscapeExecution(() -> System.exit(0))
                    .build();

            getTerminal().setImageContextProfile(
                    ImageContextProfileBuilder.start().appendImageContext(
                            new ImageContext(
                                    userNameAndPasswordInputMenu,
                                    userNameAndPasswordInputMenu.getOrigin(Terminal.CANVAS_DIM)
                            )
                    ).build()
            );
//
//
//            ScrollableMenuTextDialogImageSource connectionFailureMenu =
//                    ScrollableMenuTextDialogImageSource.builder()
//                    .setColors(
//                            Color.BLACK,
//                            Color.RED,
//                            Color.LIGHT_GRAY,
//                            Color.WHITE,
//                            Color.DARK_GRAY,
//                            Color.BLACK,
//                            Color.BLACK,
//                            Color.BLACK
//                    )
//                    .setTextHeight(36)
//                    .setTitle("Unable to contact game server!")
//                    .setOptionCount(3)
//                    .setOptionNames(
//                            "Retry",
//                            "Enter alternate host name",
//                            "Exit")
//                    .setOptionDescriptions(
//                            "Continue attempting to contact the server",
//                            "Manually enter a game server host-name",
//                            "Exit the game client"
//                    )
//                    .setOptionEnabledState(false, 1)
//                    .setOptionExecutions(
//                            WLauncherDriver::establishConnectionToRemoteHost,
//                            () -> {}, //todo - a prompt to update WLauncherEntityManager.HOST
//                            () -> System.exit(0)
//                    )
//                    .setEscapeExecution(
//                            () -> System.exit(0)
//                    )
//                    .build();
//            getTerminal().setImageContextProfile(
//                    ImageContextProfileBuilder.start().appendImageContext(
//                            new ImageContext(
//                                    connectionFailureMenu,
//                                    connectionFailureMenu.getOrigin(Terminal.CANVAS_DIM)
//                            ),
//                            KeyEvent.VK_ENTER,
//                            KeyEvent.VK_UP,
//                            KeyEvent.VK_DOWN,
//                            KeyEvent.VK_NUMPAD8,
//                            KeyEvent.VK_NUMPAD2,
//                            KeyEvent.VK_ESCAPE
//                    ).build()
//            );
        }
    }

    public static void main(String[] args) {
        getTerminal().setImageContextProfile(ImageContextProfileBuilder.start().build());
        getTerminal().start();
        establishConnectionToRemoteHost();
    }
}
