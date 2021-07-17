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
    public void handle(DataMessage dataMessage, int id) {
        System.out.println("Received message: " + dataMessage.toString());
        ArrayList<String> blocks = dataMessage.toBlocks();
        String header = blocks.get(0);
        switch (header) {
            case PUBLIC_KEY:
                System.out.println(CryptoUtilities.toAlphaNumeric(WLauncherEntityManager.getSessionKey()));
                WLauncherEntityManager.getLinkSessionManager().sendMessageOnSession(
                        DataMessageBuilder
                                .start(SESSION_KEY)
                                .addBlock(
                                        RSA.encrypt(
                                                WLauncherEntityManager.getSessionKey(),
                                                CryptoUtilities.fromAlphaNumeric(blocks.get(1))
                                        ).toString(Character.MAX_RADIX)
                                )
                                .build()
                        , id
                );
                break;
            default:
        }
    }

    @Override
    public void handleDataLinkError(Exception e, int id) {

    }

    @Override
    public void handleDataLinkClosure(int id) {

    }
}
