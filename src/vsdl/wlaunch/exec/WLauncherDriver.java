package vsdl.wlaunch.exec;

import vsdl.omnigui.image.context.ImageContextProfileBuilder;
import vsdl.wlaunch.connections.LoginProvider;
import vsdl.wlaunch.connections.RemoteConnectionProvider;

import static vsdl.wlaunch.exec.WLauncherEntityManager.*;

public class WLauncherDriver {

    public static void main(String[] args) {
        getTerminal().setImageContextProfile(ImageContextProfileBuilder.start().build());
        getTerminal().start();
        //LoginProvider.loginPrompt(true, "Password confirmation does not match!");
        RemoteConnectionProvider.establishConnectionToRemoteHost();
    }
}
