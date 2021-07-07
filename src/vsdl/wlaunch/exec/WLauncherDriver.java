package vsdl.wlaunch.exec;

import vsdl.datavector.elements.DataMessage;
import vsdl.omnigui.image.TextImager;

import java.awt.*;
import java.io.IOException;

import static vsdl.wlaunch.exec.WLauncherEntityManager.*;

public class WLauncherDriver {

    public static final boolean NETWORK = false;

    public static void main(String[] args) throws IOException {
        getGui().clearCanvas();
        if (NETWORK) {
            getDataLink().transmit(new DataMessage("{Hello world!}"));
        }
        getGui().paintCanvas(TextImager.fromChar('?', new Font(Font.SERIF, Font.ITALIC,256), Color.GREEN, Color.BLACK, true), new Point(0,0), Color.BLACK.getRGB());
        getGui().paintCanvas(TextImager.fromChar('?', new Font(Font.SERIF, Font.ITALIC,256), Color.RED, Color.BLACK, false), new Point(0,256), Color.BLACK.getRGB());
        getGui().updateFrameImage();
    }
}
