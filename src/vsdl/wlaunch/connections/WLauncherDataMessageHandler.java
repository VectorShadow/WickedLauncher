package vsdl.wlaunch.connections;

import vsdl.datavector.api.DataMessageHandler;
import vsdl.datavector.crypto.CryptoUtilities;
import vsdl.datavector.crypto.RSA;
import vsdl.datavector.elements.DataMessage;
import vsdl.datavector.elements.DataMessageBuilder;
import vsdl.wlaunch.exec.WLauncherEntityManager;
import vsdl.wlaunch.ui.providers.CreateUserProvider;
import vsdl.wlaunch.ui.providers.LogonUserProvider;

import static vsdl.wl.elements.DataMessageErrors.*;
import static vsdl.wl.elements.DataMessageHeaders.*;

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
            case LOGON_ERR:
                switch (blocks.get(1)) {
                    case LOGON_USER_DID_NOT_EXIST:
                        CreateUserProvider.createPrompt(blocks.get(2));
                        break;
                    case LOGON_INCORRECT_PASSWORD:
                        LogonUserProvider.logonPrompt("Incorrect password");
                        break;
                    default:
                        throw new IllegalArgumentException("Unrecognized Logon Error type: " + blocks.get(1));
                }
                break;
            default:
                throw new IllegalArgumentException("Unrecognized Message Type: " + header);
        }
    }

    @Override
    public void handleDataLinkError(Exception e, int id) {

    }

    @Override
    public void handleDataLinkClosure(int id) {

    }
}
