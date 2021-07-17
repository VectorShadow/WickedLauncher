package vsdl.wlaunch.connections;

import vsdl.datavector.api.DataMessageHandler;
import vsdl.datavector.elements.DataMessage;
import vsdl.datavector.elements.DataMessageBuilder;
import vsdl.datavector.link.DataLink;
import vsdl.wlaunch.exec.WLauncherEntityManager;

import static vsdl.wl.elements.DataMessageHeaders.*;

import java.util.ArrayList;

public class WLauncherDataMessageHandler implements DataMessageHandler {
    @Override
    public void handle(DataMessage dataMessage, DataLink dataLink) {
        System.out.println("Received message: " + dataMessage.toString());
        ArrayList<String> blocks = dataMessage.toBlocks();
        String header = blocks.get(0);
        switch (header) {
            case PUBLIC_KEY:
                dataLink.transmit(
                        DataMessageBuilder.start(SESSION_KEY).addBlock(WLauncherEntityManager.getSessionKey()).build()
                );
                break;
            default:
        }
    }

    @Override
    public void handleDataLinkError(Exception e, DataLink dataLink) {

    }

    @Override
    public void handleDataLinkClosure(DataLink dataLink) {

    }
}
