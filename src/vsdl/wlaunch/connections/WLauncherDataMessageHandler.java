package vsdl.wlaunch.connections;

import vsdl.datavector.api.DataMessageHandler;
import vsdl.datavector.crypto.CryptoUtilities;
import vsdl.datavector.crypto.Encryption;
import vsdl.datavector.crypto.RSA;
import vsdl.datavector.elements.DataMessage;
import vsdl.datavector.elements.DataMessageBuilder;
import vsdl.datavector.link.DataLink;
import vsdl.wlaunch.exec.WLauncherEntityManager;

import static vsdl.wl.elements.DataMessageHeaders.*;

import java.math.BigInteger;
import java.util.ArrayList;

public class WLauncherDataMessageHandler implements DataMessageHandler {
    @Override
    public void handle(DataMessage dataMessage, DataLink dataLink) {
        System.out.println("Received message: " + dataMessage.toString());
        ArrayList<String> blocks = dataMessage.toBlocks();
        String header = blocks.get(0);
        switch (header) {
            case PUBLIC_KEY:
                System.out.println(WLauncherEntityManager.getSessionKey());
                dataLink.transmit(
                        DataMessageBuilder
                                .start(SESSION_KEY)
                                .addBlock(
                                        RSA.encrypt(
                                                new BigInteger(WLauncherEntityManager.getSessionKey(), Character.MAX_RADIX),
                                                new BigInteger(blocks.get(1), Character.MAX_RADIX)
                                        ).toString(Character.MAX_RADIX)
                                )
                                .build()
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
