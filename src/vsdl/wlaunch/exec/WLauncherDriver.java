package vsdl.wlaunch.exec;

import vsdl.omnigui.image.context.ImageContext;
import vsdl.omnigui.image.context.ImageContextProfileBuilder;
import vsdl.omnigui.image.source.SimpleMenuImageSource;
import vsdl.wlaunch.ui.Terminal;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;

import static vsdl.wlaunch.exec.WLauncherEntityManager.*;

public class WLauncherDriver {

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
//        try {
//            getDataLink().transmit(new DataMessage("{Hello world!}"));
//        } catch (ConnectException e) {
//            getGui().paintCanvas(
//                    TextImager.image(
//                            "FATAL ERROR: Unable to contact game server!",
//                            36,
//                            72,
//                            400,
//                            Color.BLACK,
//                            Color.RED
//                    ),
//                    new Point(0,0),
//                    0
//            );
//        }
//        getGui().paintCanvas(
//            TextImager.image("\tMultiple Word Test? Multiple line test with special\t characters\n test.", 25, 100, 400, Color.WHITE, Color.BLACK),
//                    new Point(100, 100),
//                   0);
//        getGui().paintCanvas(
//                TextImager.imageAsTile(' ', 264, Color.BLACK, Color.LIGHT_GRAY),
//                new Point(500, 50),
//                0
//        );
//        getGui().paintCanvas(
//                TextImager.imageAsTile('@', 128, Color.WHITE, Color.BLACK),
//                new Point(504, 54),
//                0x00000000
//        );
//        getGui().paintCanvas(
//                TextImager.imageAsTile('D', 128, Color.GREEN, Color.BLACK),
//                new Point(632, 54),
//                0x00000000
//        );
//        getGui().paintCanvas(
//                TextImager.imageAsTile('T', 128, Color.DARK_GRAY, Color.BLACK),
//                new Point(504, 182),
//                0x00000000
//        );
//        getGui().paintCanvas(
//                TextImager.imageAsTile('$', 128, Color.GREEN, Color.YELLOW),
//                new Point(632, 182),
//                0x00000000
//        );
//        getGui().paintCanvas(
//                TextImager.image("[PlayerName]", 25, 25, 250, Color.GREEN, Color.BLACK),
//                new Point(512, 68),
//                0xff000000
//        );
//        getGui().paintCanvas(
//                TextImager.image("Green Dragon <Hostile>", 25, 25, 250, Color.RED, Color.BLACK),
//                new Point(625, 68),
//                0xff000000
//        );
//        getGui().paintCanvas(
//                TextImager.image("Stone Troll <Asleep>", 25, 25, 250, Color.ORANGE, Color.BLACK),
//                new Point(495, 195),
//                0xff000000
//        );
//        getGui().paintCanvas(
//                TextImager.image("A Pile of Coins", 25, 25, 250, Color.MAGENTA, Color.BLACK),
//                new Point(625, 195),
//                0xff000000
//        );
//        getGui().updateFrameImage();
    }
}
