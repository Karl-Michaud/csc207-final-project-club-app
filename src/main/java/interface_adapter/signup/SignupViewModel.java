package interface_adapter.signup;

import interface_adapter.ViewModel;

/**
 * The ViewModel for the Signup View.
 */
public class SignupViewModel extends ViewModel<SignupState> {

    public static final String TITLE_LABEL = "Sign Up View";
    public static final String CLUB_USERNAME_LABEL = "Choose username";
    public static final String EMAIL_LABEL = "Choose email";
    public static final String PASSWORD_LABEL = "Choose password";
    public static final String REPEAT_PASSWORD_LABEL = "Enter password again";

    public static final String SIGNUP_BUTTON_LABEL = "Sign up";
    public static final String CANCEL_BUTTON_LABEL = "Cancel";

    public SignupViewModel() {
        super("sign up");
        setState(new SignupState());
    }

}