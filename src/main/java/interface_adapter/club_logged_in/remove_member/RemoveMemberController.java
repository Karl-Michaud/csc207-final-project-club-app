package interface_adapter.club_logged_in.remove_member;

import use_case.club_remove_member.ClubRemoveMemberInputBoundary;
import use_case.club_remove_member.ClubRemoveMemberInputData;

/**
 * The controller for the remove member from club use case.
 */
public class RemoveMemberController {
    private final ClubRemoveMemberInputBoundary removeMemberInteractor;

    public RemoveMemberController(ClubRemoveMemberInputBoundary removeMemberInteractor) {
        this.removeMemberInteractor = removeMemberInteractor;
    }

    /**
     * Executes the remove member from club use case.
     * @param clubEmail the email of the club
     * @param studentEmail the email of the student to be removed
     */
    public void execute(String clubEmail, String studentEmail) {
        final ClubRemoveMemberInputData inputData = new ClubRemoveMemberInputData(clubEmail, studentEmail);
        removeMemberInteractor.execute(inputData);
    }
}
