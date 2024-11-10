package interface_adapter.signup.student_signup;

import interface_adapter.ViewModel;

/**
 * The ViewModel for the Signup View.
 */
public class StudentSignupViewModel extends ViewModel<StudentSignupState> {

    public static final String TITLE_LABEL = "Student Sign Up View";
    public static final String USERNAME_LABEL = "Choose username";
    public static final String EMAIL_LABEL = "Choose email";
    public static final String PASSWORD_LABEL = "Choose password";
    public static final String REPEAT_PASSWORD_LABEL = "Enter password again";

    public static final String SIGNUP_BUTTON_LABEL = "Sign up";
    public static final String CANCEL_BUTTON_LABEL = "Cancel";

    public static final String TO_LOGIN_BUTTON_LABEL = "Go to Login";

    public StudentSignupViewModel() {
        super("student sign up");
        setState(new StudentSignupState());
    }

}
