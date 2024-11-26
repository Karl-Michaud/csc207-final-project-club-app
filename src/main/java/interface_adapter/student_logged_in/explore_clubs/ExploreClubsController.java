package interface_adapter.student_logged_in.explore_clubs;

import entity.user.Club;
import use_case.explore_clubs.ExploreClubsInputBoundary;
import use_case.explore_clubs.ExploreClubsInputData;

/**
 * Controller for Explore Clubs.
 */
public class ExploreClubsController {
    private final ExploreClubsInputBoundary inputBoundary;

    public ExploreClubsController(ExploreClubsInputBoundary inputBoundary) {
        this.inputBoundary = inputBoundary;
    }

    /**
     * Invokes explore clubs use case.
     * @param email email of user.
     */
    public void execute(String email) {
        final ExploreClubsInputData inputData = new ExploreClubsInputData(email);
        inputBoundary.execute(inputData);
    }

    /**
     * Handle the selection of a club from the list.
     * @param club The selected club.
     */
    public void switchToClubView(Club club) {
        inputBoundary.switchToClubView(club);
    }

    /**
     * Switch back to the home view.
     */
    public void switchToHomeView() {
        inputBoundary.switchToHomeView();
    }
}
