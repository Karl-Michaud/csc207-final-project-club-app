package use_case.student_show_clubs;

/**
 * Interface for the output boundary of the clubs panel in StudentHome.
 */
public interface StudentShowClubsOutputBoundary {
    /**
     * Gathers all the clubs the student follows to prepare to be viewed in the StudentHomeView.
     * @param studentShowClubsOutputData all the clubs.
     */
    void prepareClubsContent(StudentShowClubsOutputData studentShowClubsOutputData);

    /**
     * Gives the presenter an error to present in case something goes wrong.
     * @param errorMessage an error message.
     */
    void prepareFailView(String errorMessage);
}
