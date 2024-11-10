package interface_adapter.signup;

import use_case.signup.club_signup.ClubSignupInputBoundary;
import use_case.signup.club_signup.ClubSignupInputData;

/**
 * Controller for the Signup Use Case.
 */
public class SignupController {

    private final SignupInputBoundary userSignupUseCaseInteractor;

    public SignupController(SignupInputBoundary userSignupUseCaseInteractor) {
        this.userSignupUseCaseInteractor = userSignupUseCaseInteractor;
    }

    /**
     * Executes the Signup Use Case.
     * @param email the email to sign up
     * @param password1 the password
     * @param password2 the password repeated
     * @param isClub true if the user to sign up is a club
     */
    public void execute(String username, String email, String password1, String password2, boolean isClub) {
        final SignupInputData signupInputData = new SignupInputData(
                username, email, password1, password2, isClub);

        userSignupUseCaseInteractor.execute(signupInputData);
    }

    /**
     * Executes the "switch to LoginView" Use Case.
     */
    public void switchToLoginView() {
        userSignupUseCaseInteractor.switchToLoginView();
    }
}
