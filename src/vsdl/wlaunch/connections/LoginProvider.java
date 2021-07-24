package vsdl.wlaunch.connections;

import vsdl.omnigui.image.context.ImageContext;
import vsdl.omnigui.image.context.ImageContextProfile;
import vsdl.omnigui.image.context.ImageContextProfileBuilder;
import vsdl.omnigui.image.source.FieldEntryTextDialogImageSource;
import vsdl.omnigui.image.source.InteractiveImageSource;
import vsdl.wlaunch.ui.Terminal;

import java.awt.*;
import java.util.Arrays;

import static vsdl.wlaunch.exec.WLauncherEntityManager.getTerminal;

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

    private static FieldEntryTextDialogImageSource loginInitial(String errorMessage) {
        return PARTIAL_LOGIN_SRC
                .setTitleColor(errorMessage == null ? Color.GREEN: Color.RED)
                .setTitle(errorMessage == null ? "Enter Username and Password:" : errorMessage)
                .setOptionCount(2)
                .setOptionNames(Arrays.copyOf(OPTIONS, 2))
                .setInputFieldCharacterLimits(Arrays.copyOf(FIELD_LIMITS, 2))
                .setInputFieldMaskState(true, 1)
                .build();
    }

    private static FieldEntryTextDialogImageSource loginCreateAccount(String errorMessage) {
        return PARTIAL_LOGIN_SRC
                .setTitleColor(errorMessage == null ? Color.GREEN: Color.RED)
                .setTitle(errorMessage == null ? "Create a New Account:" : errorMessage)
                .setOptionCount(3)
                .setOptionNames(OPTIONS)
                .setInputFieldCharacterLimits(FIELD_LIMITS)
                .setInputFieldMaskState(true, 1)
                .setInputFieldMaskState(true, 2)
                .build();
    }

    private static ImageContextProfile generateProfile(boolean isCreate, String errorMessage) {
        InteractiveImageSource src = isCreate ? loginCreateAccount(errorMessage) : loginInitial(errorMessage);
        return ImageContextProfileBuilder
                .start()
                .appendImageContext(
                        new ImageContext(
                                src,
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
}
