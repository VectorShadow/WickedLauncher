package vsdl.wlaunch.ui.providers;

import vsdl.datavector.elements.DataMessageBuilder;
import vsdl.omnigui.image.context.ImageContext;
import vsdl.omnigui.image.context.ImageContextProfile;
import vsdl.omnigui.image.context.ImageContextProfileBuilder;
import vsdl.omnigui.image.source.FieldEntryTextDialogImageSource;
import vsdl.wlaunch.ui.Terminal;

import java.awt.*;
import java.util.Arrays;

import static vsdl.wlaunch.exec.WLauncherEntityManager.*;
import static vsdl.wl.elements.DataMessageHeaders.*;

public class LogonUserProvider {

    private static final String[] OPTIONS = {
            "Username: ",
            "Password: "
    };
    private static final int[] FIELD_LIMITS = {
            16,
            24
    };

    private static String username = "";
    private static String password = "";

    private static final FieldEntryTextDialogImageSource.FieldEntryTextDialogImageSourceBuilder PARTIAL_LOGIN_SRC =
                    FieldEntryTextDialogImageSource.builder()
                            .setBackgroundColor(Color.BLACK)
                            .setEnabledColors(Color.LIGHT_GRAY, Color.BLACK)
                            .setFieldColors(Color.BLACK, Color.WHITE)
                            .setSelectedColors(Color.WHITE, Color.BLUE)
                            .setTextHeight(36)
                            .setOptionCount(2)
                            .setOptionNames(Arrays.copyOf(OPTIONS, 2))
                            .setInputFieldCharacterLimits(Arrays.copyOf(FIELD_LIMITS, 2))
                            .setInputFieldMaskState(true, 1)
                            .prePopulateField(username, 0)
                            .prePopulateField(password, 1)
                            .setSubmitExecution(LogonUserProvider::submitUsernameAndPassword)
                            .setEscapeExecution(() -> System.exit(0));

    private static final int MIN_USERNAME = 4;
    private static final int MIN_PASSWORD = 8;

    private static FieldEntryTextDialogImageSource source;

    private static boolean submissionEnabled = true;

    private static void submitUsernameAndPassword() {
        if (!submissionEnabled) {
            return;
        } else {
            submissionEnabled = false; //disable repeated login submissions
        }
        String[] fields = source.readFields();
        username = fields[0];
        password = fields[1];
        if (username.length() < MIN_USERNAME) {
            logonPrompt(
                    "Username must be at least " + MIN_USERNAME + " characters long.");
            enableSubmission();
            return;
        }
        if (password.length() < MIN_PASSWORD) {
            logonPrompt(
                    "Password must be at least " + MIN_PASSWORD + " characters long.");
            enableSubmission();
            return;
        }
        getLinkSessionManager()
                .sendMessageOnSession(
                        DataMessageBuilder
                                .start(LOGON_USER)
                                .addBlock(username)
                                .addEncryptedBlock(password, getLinkSession())
                                .build(),
                        SESSION_ID
        );
    }

    private static void loginInitial(String errorMessage) {
        source = PARTIAL_LOGIN_SRC
                .setTitleColor(errorMessage == null ? Color.GREEN: Color.RED)
                .setTitle(errorMessage == null ? "Enter Username and Password:" : errorMessage)
                .build();
    }

    private static ImageContextProfile generateProfile(String errorMessage) {
        loginInitial(errorMessage);
        return ImageContextProfileBuilder
                .start()
                .appendImageContext(
                        new ImageContext(
                                source,
                                Terminal.CANVAS_DIM
                        )
                )
                .build();
    }

    public static void logonPrompt() {
        logonPrompt(null);
    }


    public static void logonPrompt(String errorMessage) {
        enableSubmission();
        getTerminal().setImageContextProfile(generateProfile(errorMessage));
    }

    public static void enableSubmission() {
        submissionEnabled = true;
    }
}
