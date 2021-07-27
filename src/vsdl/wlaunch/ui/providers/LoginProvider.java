package vsdl.wlaunch.ui.providers;

import vsdl.datavector.crypto.CryptoUtilities;
import vsdl.datavector.elements.DataMessageBuilder;
import vsdl.omnigui.image.context.ImageContext;
import vsdl.omnigui.image.context.ImageContextProfile;
import vsdl.omnigui.image.context.ImageContextProfileBuilder;
import vsdl.omnigui.image.source.FieldEntryTextDialogImageSource;
import vsdl.wl.elements.SecurityConstants;
import vsdl.wlaunch.ui.Terminal;

import java.awt.*;
import java.util.Arrays;

import static vsdl.wlaunch.exec.WLauncherEntityManager.*;
import static vsdl.wl.elements.DataMessageHeaders.*;

public class LoginProvider {
    private static final FieldEntryTextDialogImageSource.FieldEntryTextDialogImageSourceBuilder PARTIAL_LOGIN_SRC =
                    FieldEntryTextDialogImageSource.builder()
                            .setBackgroundColor(Color.BLACK)
                            .setEnabledColors(Color.LIGHT_GRAY, Color.BLACK)
                            .setFieldColors(Color.BLACK, Color.WHITE)
                            .setSelectedColors(Color.WHITE, Color.BLUE)
                            .setTextHeight(36)
                            .setEscapeExecution(() -> System.exit(0));

    private static final String[] OPTIONS = {
            "Username: ",
            "Password: ",
            "Confirm: "
    };
    private static final int[] FIELD_LIMITS = {
            16,
            24,
            24
    };

    private static final int MIN_USERNAME = 4;
    private static final int MIN_PASSWORD = 8;

    private static FieldEntryTextDialogImageSource source;

    private static boolean allowSubmission = true;

    private static String username = "";
    private static String password = "";
    private static String confirm = "";

    private static void submitUsernameAndPassword(boolean requireConfirmation) {
        if (!allowSubmission) {
            return;
        } else {
            allowSubmission = false; //disable repeated login submissions
        }
        String[] fields = source.readFields();
        username = fields[0];
        password = fields[1];
        if (requireConfirmation) {
            confirm = fields[2];
        }
        if (username.length() < MIN_USERNAME) {
            loginPrompt(
                    requireConfirmation,
                    "Username must be at least " + MIN_USERNAME + " characters long.");
            enableSubmission();
            return;
        }
        if (password.length() < MIN_PASSWORD) {
            loginPrompt(
                    requireConfirmation,
                    "Password must be at least " + MIN_PASSWORD + " characters long.");
            enableSubmission();
            return;
        }
        if (requireConfirmation) {
            if (!password.equals(confirm)) {
                loginPrompt(true, "Password confirmation does not match!");
                enableSubmission();
                return;
            }
        }
        getLinkSessionManager()
                .sendMessageOnSession(
                        DataMessageBuilder
                                .start(
                                        requireConfirmation
                                                ? CREATE_ACCOUNT
                                                : LOGIN_ACCOUNT
                                )
                                .addBlock(username)
                                .addEncryptedBlock(
                                        password,
                                        getLinkSession()
                                ).build(),
                        SESSION_ID
        );
    }

    private static void submitLoginRequest() {
        submitUsernameAndPassword(false);
    }

    private static void submitCreateRequest() {
        submitUsernameAndPassword(true);
    }

    private static void loginInitial(String errorMessage) {
        source = PARTIAL_LOGIN_SRC
                .setTitleColor(errorMessage == null ? Color.GREEN: Color.RED)
                .setTitle(errorMessage == null ? "Enter Username and Password:" : errorMessage)
                .setOptionCount(2)
                .setOptionNames(Arrays.copyOf(OPTIONS, 2))
                .setInputFieldCharacterLimits(Arrays.copyOf(FIELD_LIMITS, 2))
                .setInputFieldMaskState(true, 1)
                .prePopulateField(username, 0)
                .prePopulateField(password, 1)
                .setSubmitExecution(LoginProvider::submitLoginRequest)
                .build();
    }

    private static void loginCreateAccount(String errorMessage) {
        source = PARTIAL_LOGIN_SRC
                .setTitleColor(errorMessage == null ? Color.GREEN: Color.RED)
                .setTitle(errorMessage == null ? "Create a New Account:" : errorMessage)
                .setOptionCount(3)
                .setOptionNames(OPTIONS)
                .setInputFieldCharacterLimits(FIELD_LIMITS)
                .setInputFieldMaskState(true, 1)
                .setInputFieldMaskState(true, 2)
                .prePopulateField(username, 0)
                .prePopulateField(password, 1)
                .prePopulateField(confirm, 2)
                .setSubmitExecution(LoginProvider::submitCreateRequest)
                .build();
    }

    private static ImageContextProfile generateProfile(boolean isCreate, String errorMessage) {
        if (isCreate) {
            loginCreateAccount(errorMessage);
        } else {
            loginInitial(errorMessage);
        }
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

    public static void loginPrompt(boolean isCreate) {
        loginPrompt(isCreate, null);
    }


    public static void loginPrompt(boolean isCreate, String errorMessage) {
        getTerminal().setImageContextProfile(generateProfile(isCreate, errorMessage));
    }

    public static void enableSubmission() {
        allowSubmission = true;
    }
}
