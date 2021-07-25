package vsdl.wlaunch.exec;

import vsdl.omnigui.image.context.ImageContextProfileBuilder;
import vsdl.wlaunch.ui.providers.RemoteConnectionProvider;

import static vsdl.wlaunch.exec.WLauncherEntityManager.*;

public class WLauncherDriver {

    public static void main(String[] args) {
        getTerminal().setImageContextProfile(ImageContextProfileBuilder.start().build());
        getTerminal().start();
        RemoteConnectionProvider.establishConnectionToRemoteHost();
    }
}
