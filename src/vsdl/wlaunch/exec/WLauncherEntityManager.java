package vsdl.wlaunch.exec;

import vsdl.datavector.crypto.CryptoUtilities;
import vsdl.datavector.link.LinkSession;
import vsdl.datavector.link.LinkSessionManager;
import vsdl.wlaunch.connections.WLauncherDataMessageHandler;
import vsdl.wlaunch.ui.Terminal;

import java.io.IOException;
import java.math.BigInteger;
import java.net.Socket;

public class WLauncherEntityManager {

    public static final String HOST_NAME = "167.114.97.153";
    public static final int HOST_PORT = 31592;

    public static final int SESSION_ID = 1;

    private static LinkSessionManager linkSessionManager = null;

    private static BigInteger sessionKey = null;

    private static Terminal term = null;


    public static LinkSessionManager getLinkSessionManager() {
        if (linkSessionManager == null) {
            try {
                Socket s = new Socket(HOST_NAME, HOST_PORT);
                linkSessionManager = new LinkSessionManager(new WLauncherDataMessageHandler());
                linkSessionManager.addSession(s);
            } catch (IOException e) {
                throw new IllegalStateException("Failed to instantiate network session.");
            }
        }
        return linkSessionManager;
    }

    public static LinkSession getLinkSession() {
        if (linkSessionManager == null) {
            getLinkSessionManager();
        }
        return linkSessionManager.getSessionByID(SESSION_ID);
    }

    public static BigInteger getSessionKey() {
        if (sessionKey == null) {
            sessionKey = CryptoUtilities.randomKey(128);
            getLinkSession().setSessionSecret(sessionKey);
        }
        return sessionKey;
    }

    public static Terminal getTerminal() {
        if (term == null) {
            term = new Terminal();
        }
        return term;
    }
}
