package use_case.signup.club_signup;

import entity.user.Club;
import entity.user.ClubUserFactory;

/**
 * The Club Signup Interactor.
 */
public class ClubSignupInteractor implements ClubSignupInputBoundary {
    private final ClubSignupUserDataAccessInterface userDataAccessObject;
    private final ClubSignupOutputBoundary userPresenter;
    private final ClubUserFactory clubUserFactory;

    public ClubSignupInteractor(ClubSignupUserDataAccessInterface signupDataAccessInterface,
                                ClubSignupOutputBoundary clubSignupOutputBoundary,
                                ClubUserFactory clubUserFactory) {
        this.userDataAccessObject = signupDataAccessInterface;
        this.userPresenter = clubSignupOutputBoundary;
        this.clubUserFactory = clubUserFactory;
    }

    @Override
    public void execute(ClubSignupInputData clubSignupInputData) {
        if (userDataAccessObject.existsByNameClub(clubSignupInputData.getUsername())) {
            userPresenter.prepareFailView("Username already exists.");
        }
        else if (userDataAccessObject.existsByEmailClub(clubSignupInputData.getEmail())) {
            userPresenter.prepareFailView("Email address already exists.");
        }
        else if (!clubSignupInputData.getPassword().equals(clubSignupInputData.getRepeatPassword())) {
            userPresenter.prepareFailView("Passwords don't match.");
        }
        else {

            final Club user = clubUserFactory.create(clubSignupInputData.getUsername(),
                    clubSignupInputData.getEmail(),
                    clubSignupInputData.getPassword());

            userDataAccessObject.saveClub(user);

            final ClubSignupOutputData clubSignupOutputData = new ClubSignupOutputData(user.getEmail(),
                    false);
            userPresenter.prepareSuccessView(clubSignupOutputData);
        }
    }

    @Override
    public void switchToLoginView() {
        userPresenter.switchToLoginView();
    }
}
