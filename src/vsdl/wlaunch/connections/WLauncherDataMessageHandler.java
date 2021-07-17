package vsdl.wlaunch.connections;

import vsdl.datavector.api.DataMessageHandler;
import vsdl.datavector.elements.DataMessage;
import vsdl.datavector.link.DataLink;

public class WLauncherDataMessageHandler implements DataMessageHandler {
    @Override
    public void handle(DataMessage dataMessage, DataLink dataLink) {
        System.out.println("Received message: " + dataMessage.toString());
    }

    @Override
    public void handleDataLinkError(Exception e, DataLink dataLink) {

    }

    @Override
    public void handleDataLinkClosure(DataLink dataLink) {

    }
}
