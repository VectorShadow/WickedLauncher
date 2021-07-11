package vsdl.wlaunch.exec;

import vsdl.datavector.link.DataLink;
import vsdl.wlaunch.connections.WLauncherDataMessageHandler;
import vsdl.wlaunch.ui.Terminal;

import java.io.IOException;
import java.net.Socket;

public class WLauncherEntityManager {

    public static final String HOST_NAME = "167.114.97.153";
    public static final int HOST_PORT = 31592;

    private static DataLink DATA_LINK = null;

    private static Terminal TERM = null;

    public static DataLink getDataLink() throws IOException {
        if (DATA_LINK == null) {
            DATA_LINK = new DataLink(
                    new Socket(HOST_NAME, HOST_PORT),
                    new WLauncherDataMessageHandler()
            );
            DATA_LINK.start();
        }
        return DATA_LINK;
    }

    public static Terminal getTerminal() {
        if (TERM == null) {
            TERM = new Terminal();
        }
        return TERM;
    }
}
