package vsdl.wlaunch.ui.providers;

import vsdl.datavector.elements.DataMessageBuilder;
import vsdl.omnigui.image.context.ImageContext;
import vsdl.omnigui.image.context.ImageContextProfile;
import vsdl.omnigui.image.context.ImageContextProfileBuilder;
import vsdl.omnigui.image.source.FieldEntryTextDialogImageSource;
import vsdl.wlaunch.ui.Terminal;

import java.awt.*;
import java.util.Arrays;

import static vsdl.wl.elements.DataMessageHeaders.CREATE_USER;
import static vsdl.wlaunch.exec.WLauncherEntityManager.*;

public class CreateUserProvider {

    private static final String[] OPTIONS = {
            "Enter Password: ",
            "Confirm Password: "
    };
    private static final int[] FIELD_LIMITS = {
            24,
            24
    };

    private static String password1 = "";
    private static String password2 = "";

    private static final FieldEntryTextDialogImageSource.FieldEntryTextDialogImageSourceBuilder PARTIAL_CREATE_SRC =
            FieldEntryTextDialogImageSource.builder()
                    .setBackgroundColor(Color.BLACK)
                    .setEnabledColors(Color.LIGHT_GRAY, Color.BLACK)
                    .setFieldColors(Color.BLACK, Color.WHITE)
                    .setSelectedColors(Color.WHITE, Color.BLUE)
                    .setTextHeight(36)
                    .setOptionCount(2)
                    .setOptionNames(Arrays.copyOf(OPTIONS, 2))
                    .setInputFieldCharacterLimits(Arrays.copyOf(FIELD_LIMITS, 2))
                    .setInputFieldMaskState(true, 0)
                    .setInputFieldMaskState(true, 1)
                    .prePopulateField(password1, 0)
                    .prePopulateField(password2, 1)
                    .setSubmitExecution(CreateUserProvider::submitCreateRequest)
                    .setEscapeExecution(LogonUserProvider::logonPrompt);

    private static final int MIN_PASSWORD = 8;

    private static FieldEntryTextDialogImageSource source;

    private static boolean submissionEnabled = true;

    private static String username = "";

    private static void submitCreateRequest() {
        if (!submissionEnabled) {
            return;
        } else {
            submissionEnabled = false; //disable repeated login submissions
        }
        String[] fields = source.readFields();
        password1 = fields[0];
        password2 = fields[1];
        if (password1.length() < MIN_PASSWORD) {
            createPrompt(
                    username,
                    "Password must be at least " + MIN_PASSWORD + " characters long.");
            enableSubmission();
            return;
        }
        if (!password1.equalsIgnoreCase(password2)) {
            createPrompt(
                    username,
                    "Passwords must match.");
            enableSubmission();
            return;
        }
        getLinkSessionManager()
                .sendMessageOnSession(
                        DataMessageBuilder
                                .start(
                                        CREATE_USER
                                )
                                .addBlock(username)
                                .addEncryptedBlock(
                                        password1,
                                        getLinkSession()
                                ).build(),
                        SESSION_ID
                );
    }

    private static void createInitial(String username, String errorMessage) {
        CreateUserProvider.username = username;
        source = PARTIAL_CREATE_SRC
                .setTitleColor(errorMessage == null ? Color.GREEN: Color.RED)
                .setTitle("Create account { " + username + " } - " + (errorMessage == null ? "Confirm password:" : errorMessage))
                .build();
    }

    private static ImageContextProfile generateProfile(String username, String errorMessage) {
        createInitial(username, errorMessage);
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

    public static void createPrompt(String username) {
        createPrompt(username, null);
    }


    public static void createPrompt(String username, String errorMessage) {
        enableSubmission();
        getTerminal().setImageContextProfile(generateProfile(username, errorMessage));
    }

    public static void enableSubmission() {
        submissionEnabled = true;
    }
}
