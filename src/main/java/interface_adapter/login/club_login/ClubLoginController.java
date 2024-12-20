package interface_adapter.login.club_login;

import use_case.login.club_login.ClubLoginInputBoundary;
import use_case.login.club_login.ClubLoginInputData;

/**
 * The Controller for the Club Login Use Case.
 */
public class ClubLoginController {
    private final ClubLoginInputBoundary loginUseCaseInteractor;

    public ClubLoginController(ClubLoginInputBoundary loginUseCaseInteractor) {
        this.loginUseCaseInteractor = loginUseCaseInteractor;
    }

    /**
     * Executes the Club Login Use Case.
     * @param email the email of the user logging in
     * @param password the password of the user logging in
     */
    public void execute(String email, String password) {
        final ClubLoginInputData loginInputData = new ClubLoginInputData(email, password);

        loginUseCaseInteractor.execute(loginInputData);
    }

    /**
     * Executes the "switch to ClubSignupView" Use Case.
     */
    public void switchToClubSignupView() {
        loginUseCaseInteractor.switchToClubSignupView();
    }
}
