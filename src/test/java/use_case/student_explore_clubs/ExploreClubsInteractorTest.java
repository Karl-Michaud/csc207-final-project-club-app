package use_case.student_explore_clubs;

import data_access.InMemoryUserDataAccessObject;
import entity.user.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.*;

public class ExploreClubsInteractorTest {
    @Test
    void successTest() {
        // In-memory data setup
        InMemoryUserDataAccessObject userRepository = new InMemoryUserDataAccessObject();
        // Create clubs
        ClubFactory clubFactory = new ClubUserFactory();
        Club club1 = clubFactory.create("Club1", "club1@example.com", "password1");
        Club club2 = clubFactory.create("Club2", "club2@example.com", "password2");
        club1.setClubDescription("Description1");
        club2.setClubDescription("Description2");

        // Create a student and associate with one club
        StudentFactory studentFactory = new StudentUserFactory();
        Student student = studentFactory.create("Student1", "student1@example.com", "password");
        student.joinClub(club1);
        club1.addClubMember(student);

        userRepository.saveStudent(student);
        userRepository.saveClub(club1);
        userRepository.saveClub(club2);

        // Input data
        ExploreClubsInputData inputData = new ExploreClubsInputData("student1@example.com");

        // Output boundary to verify success
        ExploreClubsOutputBoundary successPresenter = new ExploreClubsOutputBoundary() {
            @Override
            public void prepareSuccessView(ExploreClubsOutputData data) {
                assertEquals("student1@example.com", data.getStudentEmail());
                assertFalse(data.isUseCaseFailed());
                assertEquals(1, data.getNotJoinedClubs().size());
                ArrayList<String> joinTest = new ArrayList<>();
                joinTest.add("club1@example.com");
                assertEquals(joinTest, data.getJoinedClubsEmails());

                Map<String, String> club = data.getNotJoinedClubs().get(0);
                assertEquals("Club2", club.get("username"));
                assertEquals("club2@example.com", club.get("email"));
                assertEquals("Description2", club.get("description"));
                assertEquals("0", club.get("numMembers"));
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("Unexpected fail call");
            }

            @Override
            public void switchToClubView(Map<String, String> club) {
                // Not used in this test
                // Cannot test the view.
                fail("Shouldn't run this.");
            }

            @Override
            public void switchToHomeView() {
                // Not used in this test
                // Cannot test the view.
                fail("Shouldn't run this.");
            }
        };

        // Interactor execution
        ExploreClubsInteractor interactor = new ExploreClubsInteractor(userRepository,
                successPresenter, userRepository);
        interactor.execute(inputData);
    }

    @Test
    void failTest() {
        // In-memory data setup
        InMemoryUserDataAccessObject userRepository = new InMemoryUserDataAccessObject();

        // Input data for a non-existent student
        ExploreClubsInputData inputData = new ExploreClubsInputData("nonexistent@example.com");

        // Output boundary to verify failure
        ExploreClubsOutputBoundary failPresenter = new ExploreClubsOutputBoundary() {
            @Override
            public void prepareSuccessView(ExploreClubsOutputData data) {
                fail("Unexpected success call");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("nonexistent@example.com: Account does not exist.", errorMessage);
            }

            @Override
            public void switchToClubView(Map<String, String> club) {
                // Not used in this test
                fail("Shouldn't run this.");
            }

            @Override
            public void switchToHomeView() {
                // Not used in this test
                fail("Shouldn't run this.");
            }
        };

        // Interactor execution
        ExploreClubsInteractor interactor = new ExploreClubsInteractor(userRepository,
                failPresenter, userRepository);
        interactor.execute(inputData);
    }

    @Test
    public void testSwitchToClubView() {
        // In-memory data setup
        InMemoryUserDataAccessObject userRepository = new InMemoryUserDataAccessObject();

        // Output boundary to verify failure
        ExploreClubsOutputBoundary failPresenter = new ExploreClubsOutputBoundary() {
            @Override
            public void prepareSuccessView(ExploreClubsOutputData data) {
                fail("Unexpected success call");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("nonexistent@example.com: Account does not exist.", errorMessage);
            }

            @Override
            public void switchToClubView(Map<String, String> club) {
                // running this test
            }

            @Override
            public void switchToHomeView() {
                // Not used in this test
                fail("Shouldn't run this.");
            }
        };
        Map<String, String> club = new HashMap<>();
        club.put("Test", "test");
        // Interactor execution
        ExploreClubsInteractor interactor = new ExploreClubsInteractor(userRepository,
                failPresenter, userRepository);
        interactor.switchToClubView(club);
    }

    @Test
    public void testSwitchToHomeView() {
        // In-memory data setup
        InMemoryUserDataAccessObject userRepository = new InMemoryUserDataAccessObject();

        // Output boundary to verify failure
        ExploreClubsOutputBoundary failPresenter = new ExploreClubsOutputBoundary() {
            @Override
            public void prepareSuccessView(ExploreClubsOutputData data) {
                fail("Unexpected success call");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("nonexistent@example.com: Account does not exist.", errorMessage);
            }

            @Override
            public void switchToClubView(Map<String, String> club) {
                // Not used in this test
                fail("Shouldn't run this.");
            }

            @Override
            public void switchToHomeView() {
                // Running this test
            }
        };
        Map<String, String> club = new HashMap<>();
        club.put("Test", "test");
        // Interactor execution
        ExploreClubsInteractor interactor = new ExploreClubsInteractor(userRepository,
                failPresenter, userRepository);
        interactor.switchToHomeView();
    }
}