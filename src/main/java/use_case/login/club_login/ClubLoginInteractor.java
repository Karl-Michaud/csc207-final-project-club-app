package use_case.login.club_login;

import entity.user.Club;

/**
 * The interactor for the login use case for clubs.
 */
public class ClubLoginInteractor implements ClubLoginInputBoundary {
    private final ClubLoginDataAccessInterface clubDataAccessObject;
    private final ClubLoginOutputBoundary clubLoginPresenter;

    public ClubLoginInteractor(ClubLoginDataAccessInterface clubDataAccessObject,
                               ClubLoginOutputBoundary clubLoginPresenter) {
        this.clubDataAccessObject = clubDataAccessObject;
        this.clubLoginPresenter = clubLoginPresenter;
    }

    /**
     * Executes the login use case for the club user.
     * @param clubLoginInputData the input data
     */
    public void execute(ClubLoginInputData clubLoginInputData) {
        final String email = clubLoginInputData.getEmail();
        final String password = clubLoginInputData.getPassword();
        if (email.isEmpty() || password.isEmpty()) {
            clubLoginPresenter.prepareFailView("Empty text field(s).");
        }
        else if (!clubDataAccessObject.existsByEmailClub(email)) {
            clubLoginPresenter.prepareFailView(email + ": Account does not exist.");
        }
        else {
            final String pwd = clubDataAccessObject.getClub(email).getPassword();
            if (!pwd.equals(password)) {
                clubLoginPresenter.prepareFailView("Incorrect password for \"" + email + "\".");
            }
            else {
                final Club club = clubDataAccessObject.getClub(email);
                final ClubLoginOutputData loginOutputData = new ClubLoginOutputData(club.getUsername(),
                        club.getEmail(), club.getClubDescription(), false);
                clubLoginPresenter.prepareSuccessView(loginOutputData);
            }
        }
    }

    @Override
    public void switchToClubSignupView() {
        clubLoginPresenter.switchToClubSignupView();
    }
}
