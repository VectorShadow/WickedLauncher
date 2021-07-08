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
            getGui().paintCanvas(TextImager.fromString("FATAL ERROR: Unable to contact game server!", 40, 68, 2048, Color.BLACK, Color.RED) , new Point(0,0), Color.BLACK.getRGB());
        }
//        getGui().paintCanvas(TextImager.fromChar('?', new Font(Font.SERIF, Font.ITALIC,256), Color.GREEN, Color.BLACK, true), new Point(0,0), Color.BLACK.getRGB());
//        getGui().paintCanvas(TextImager.fromChar('?', new Font(Font.SERIF, Font.ITALIC,256), Color.RED, Color.BLACK, false), new Point(0,256), Color.BLACK.getRGB());
//        getGui().paintCanvas(TextImager.fromString("Test text string of many chars exceeding width", new Font(Font.SERIF, Font.ITALIC,12), 135, 900, Color. WHITE, Color.BLACK), new Point(0,0), Color.BLACK.getRGB());
//        getGui().paintCanvas(TextImager.fromString("Overlap test text", new Font(Font.SERIF, Font.ITALIC,12), 135, 655, Color.RED, Color.BLACK), new Point(100,15), Color.BLACK.getRGB());
        getGui().updateFrameImage();
    }
}
