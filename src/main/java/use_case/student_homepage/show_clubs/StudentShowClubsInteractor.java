package use_case.student_homepage.show_clubs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import entity.data_structure.DataStoreArrays;
import entity.user.Club;

/**
 * Interactor for the show clubs usecase.
 */
public class StudentShowClubsInteractor implements StudentShowClubsInputBoundary {
    private final StudentShowClubsOutputBoundary showClubsPresenter;
    private final StudentShowClubsAccessInterface studentShowClubsAccessInterface;

    public StudentShowClubsInteractor(StudentShowClubsOutputBoundary showClubsPresenter,
                                      StudentShowClubsAccessInterface studentShowClubsAccessInterface) {
        this.showClubsPresenter = showClubsPresenter;
        this.studentShowClubsAccessInterface = studentShowClubsAccessInterface;
    }

    @Override
    public void execute(StudentShowClubsInputData studentShowClubsInputData) {
        final String currUserEmail = studentShowClubsInputData.getUserEmail();
        if (!studentShowClubsAccessInterface.existsByEmailStudent(currUserEmail)) {
            showClubsPresenter.prepareFailView("The account does not exist.");
        }
        else {
            final DataStoreArrays<Club> clubs = (DataStoreArrays<Club>) studentShowClubsAccessInterface.getStudent(
                    currUserEmail).getJoinedClubs();
            final ArrayList<Map<String, String>> clubData = new ArrayList<>();
            for (final Club club : clubs) {
                final Map<String, String> singleClubData = new HashMap<>();
                singleClubData.put("username", club.getUsername());
                singleClubData.put("email", club.getEmail());
                singleClubData.put("description", club.getClubDescription());
                clubData.add(singleClubData);
            }
            final StudentShowClubsOutputData studentShowClubsOutputData = new StudentShowClubsOutputData(clubData, currUserEmail);
            showClubsPresenter.prepareClubsContent(studentShowClubsOutputData);
        }
    }
}
