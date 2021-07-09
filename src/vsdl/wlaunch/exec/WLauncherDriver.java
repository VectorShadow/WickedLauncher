package vsdl.wlaunch.exec;

import vsdl.datavector.elements.DataMessage;
import vsdl.omnigui.image.TextImager;

import java.awt.*;
import java.io.IOException;
import java.net.ConnectException;

import static vsdl.wlaunch.exec.WLauncherEntityManager.*;

public class WLauncherDriver {

    public static void main(String[] args) throws IOException {
        getGui().clearCanvas();
        try {
            getDataLink().transmit(new DataMessage("{Hello world!}"));
        } catch (ConnectException e) {
            getGui().paintCanvas(
                    TextImager.image(
                            "FATAL ERROR: Unable to contact game server!",
                            36,
                            72,
                            400,
                            Color.BLACK,
                            Color.RED
                    ),
                    new Point(0,0),
                    0
            );
        }
        getGui().paintCanvas(
            TextImager.image("\tMultiple Word Test? Multiple line test with special\t characters\n test.", 25, 100, 400, Color.WHITE, Color.BLACK),
                    new Point(100, 100),
                   0);
        getGui().paintCanvas(
                TextImager.imageAsTile(' ', 264, Color.BLACK, Color.LIGHT_GRAY),
                new Point(500, 50),
                0
        );
        getGui().paintCanvas(
                TextImager.imageAsTile('@', 128, Color.WHITE, Color.BLACK),
                new Point(504, 54),
                0x00000000
        );
        getGui().paintCanvas(
                TextImager.imageAsTile('D', 128, Color.GREEN, Color.BLACK),
                new Point(632, 54),
                0x00000000
        );
        getGui().paintCanvas(
                TextImager.imageAsTile('T', 128, Color.DARK_GRAY, Color.BLACK),
                new Point(504, 182),
                0x00000000
        );
        getGui().paintCanvas(
                TextImager.imageAsTile('$', 128, Color.GREEN, Color.YELLOW),
                new Point(632, 182),
                0x00000000
        );
        getGui().paintCanvas(
                TextImager.image("[PlayerName]", 25, 25, 250, Color.GREEN, Color.BLACK),
                new Point(512, 68),
                0xff000000
        );
        getGui().paintCanvas(
                TextImager.image("Green Dragon <Hostile>", 25, 25, 250, Color.RED, Color.BLACK),
                new Point(625, 68),
                0xff000000
        );
        getGui().paintCanvas(
                TextImager.image("Stone Troll <Asleep>", 25, 25, 250, Color.ORANGE, Color.BLACK),
                new Point(495, 195),
                0xff000000
        );
        getGui().paintCanvas(
                TextImager.image("A Pile of Coins", 25, 25, 250, Color.MAGENTA, Color.BLACK),
                new Point(625, 195),
                0xff000000
        );
        getGui().updateFrameImage();
    }
}
