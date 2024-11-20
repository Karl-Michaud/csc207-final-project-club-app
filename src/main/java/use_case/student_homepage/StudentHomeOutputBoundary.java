package use_case.student_homepage;

/**
 * Interface for the StudentHome output boundary.
 */
public interface StudentHomeOutputBoundary {

    /**
     * Gathers necessary content to prepare the content on the HomeView.
     * @param studentHomeOutputData data needed for the view.
     */
    void prepareClubPostContent(StudentHomeOutputData studentHomeOutputData);

    /**
     * Prepares a failed view incase something goes wrong.
     * @param errorMessage the error message relating to the issue.
     */
    void prepareFailView(String errorMessage);

    /**
     * Switches to the Login View.
     */
    void switchToLoginView();

    /**
     * Switches to the Profile View.
     */
    void switchToProfileView();
}
