package use_case.student_homepage.like;

import data_access.InMemoryUserDataAccessObject;
import entity.post.AnnouncementFactory;
import entity.post.Post;
import entity.post.PostFactory;
import entity.user.*;
import org.junit.jupiter.api.Test;
import use_case.student_like.StudentLikeInputData;
import use_case.student_like.StudentLikeInteractor;
import use_case.student_like.StudentLikeOutputBoundary;
import use_case.student_like.StudentLikeOutputData;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class StudentLikeInteractorTest {
    @Test
    void successLikedTest() {
        // Set up in-memory repository
        InMemoryUserDataAccessObject dao = new InMemoryUserDataAccessObject();

        // Create a club
        ClubFactory clubFactory = new ClubUserFactory();
        Club club = clubFactory.create("Photography Club", "photo@university.com", "password");
        club.setClubDescription("For photography enthusiasts.");

        // Create a student in the club
        StudentFactory studentFactory = new StudentUserFactory();
        Student student = studentFactory.create("Alice", "alice@university.com", "password");
        student.joinClub(club);
        club.addClubMember(student);


        PostFactory postFactory = new AnnouncementFactory();
        // Create a post for the club
        Post post = postFactory.create("Black and White Photo contest announcement.", "We're planning to host"
                + "a photo contest around the theme black and white photos and would like to hear your feedback!" +
                " Get creative," + "ditch the colors, and win prizes!");
        club.addClubPost(post);
        dao.saveClub(club);
        dao.savePost(post, club);
        dao.saveStudent(student);

        // Input Data
        Map<String, Object> postData = new HashMap<String, Object>();
        postData.put("title", post.getTitle());
        postData.put("content", post.getContent());
        postData.put("likes", post.numberOfLikes());
        postData.put("dislikes", post.numberOfDislikes());
        postData.put("liked", post.getLikes().contains(student.getEmail()));
        postData.put("disliked", post.getDislikes().contains(student.getEmail()));
        postData.put("club-email", club.getEmail());
        postData.put("time", post.timeOfPosting());
        postData.put("date", post.dateOfPosting());
        StudentLikeInputData inputData = new StudentLikeInputData(student.getEmail(), postData);

        // Output boundary for success verification
        StudentLikeOutputBoundary successPresenter = new StudentLikeOutputBoundary() {
            @Override
            public void prepareSuccessView(StudentLikeOutputData data) {
                assertEquals(postData, data.getPostData());
                assertEquals("Photography Club", data.getClubName());
                assertTrue((Boolean) data.getPostData().get("liked"));
            }

            @Override
            public void prepareErrorView(String errorMessage) {
                fail("Unexpected fail call");
            }
        };
        // Interactor execution
        StudentLikeInteractor interactor = new StudentLikeInteractor(dao, dao, successPresenter);
        interactor.execute(inputData);
    }

    @Test
    void successUnlikedTest() {
        // Set up in-memory repository
        InMemoryUserDataAccessObject dao = new InMemoryUserDataAccessObject();

        // Create a club
        ClubFactory clubFactory = new ClubUserFactory();
        Club club = clubFactory.create("Photography Club", "photo@university.com", "password");
        club.setClubDescription("For photography enthusiasts.");

        // Create a student in the club
        StudentFactory studentFactory = new StudentUserFactory();
        Student student = studentFactory.create("Alice", "alice@university.com", "password");
        student.joinClub(club);
        club.addClubMember(student);

        PostFactory postFactory = new AnnouncementFactory();
        // Create a post for the club
        Post post = postFactory.create("Black and White Photo contest announcement.", "We're planning to host"
                + "a photo contest around the theme black and white photos and would like to hear your feedback!" +
                " Get creative," + "ditch the colors, and win prizes!");
        post.addLike(student);
        dao.saveClub(club);
        dao.savePost(post, club);
        dao.saveStudent(student);
        // Input Data
        Map<String, Object> postData = new HashMap<String, Object>();
        postData.put("title", post.getTitle());
        postData.put("content", post.getContent());
        postData.put("likes", post.numberOfLikes());
        postData.put("dislikes", post.numberOfDislikes());
        postData.put("liked", post.getLikes().contains(student.getEmail()));
        postData.put("disliked", post.getDislikes().contains(student.getEmail()));
        postData.put("club-email", club.getEmail());
        postData.put("time", post.timeOfPosting());
        postData.put("date", post.dateOfPosting());
        StudentLikeInputData inputData = new StudentLikeInputData(student.getEmail(), postData);

        // Output boundary for success verification
        StudentLikeOutputBoundary successPresenter = new StudentLikeOutputBoundary() {
            @Override
            public void prepareSuccessView(StudentLikeOutputData data) {
                assertEquals(postData, data.getPostData());
                assertEquals("Photography Club", data.getClubName());
                assertFalse((Boolean) data.getPostData().get("liked"));
            }

            @Override
            public void prepareErrorView(String errorMessage) {
                fail("Unexpected fail call");
            }
        };
        // Interactor execution
        StudentLikeInteractor interactor = new StudentLikeInteractor(dao, dao, successPresenter);
        interactor.execute(inputData);
    }

    @Test
    void testPostNotFound() {
        // Set up in-memory repository
        InMemoryUserDataAccessObject dao = new InMemoryUserDataAccessObject();

        // Create a club
        ClubFactory clubFactory = new ClubUserFactory();
        Club club = clubFactory.create("Photography Club", "photo@university.com", "password");
        club.setClubDescription("For photography enthusiasts.");

        // Create a student in the club
        StudentFactory studentFactory = new StudentUserFactory();
        Student student = studentFactory.create("Alice", "alice@university.com", "password");
        student.joinClub(club);
        club.addClubMember(student);

        PostFactory postFactory = new AnnouncementFactory();
        // Create a post for the club
        Post post = postFactory.create("Black and White Photo contest announcement.", "Details about the contest...");
        club.addClubPost(post);
        dao.saveClub(club);
        dao.savePost(post, club);
        dao.saveStudent(student);

        // Input Data with incorrect post date/time
        Map<String, Object> postData = new HashMap<>();
        postData.put("title", post.getTitle());
        postData.put("content", post.getContent());
        postData.put("likes", post.numberOfLikes());
        postData.put("dislikes", post.numberOfDislikes());
        postData.put("liked", post.getLikes().contains(student.getEmail()));
        postData.put("disliked", post.getDislikes().contains(student.getEmail()));
        postData.put("club-email", club.getEmail());
        postData.put("time", "12:00:00"); // Incorrect time
        postData.put("date", "2022-01-01"); // Incorrect date

        StudentLikeInputData inputData = new StudentLikeInputData(student.getEmail(), postData);

        // Output boundary for error verification
        StudentLikeOutputBoundary errorPresenter = new StudentLikeOutputBoundary() {
            @Override
            public void prepareSuccessView(StudentLikeOutputData data) {
                fail("Unexpected success call");
            }

            @Override
            public void prepareErrorView(String errorMessage) {
                assertEquals("post does not exist", errorMessage);
            }
        };

        // Interactor execution
        StudentLikeInteractor interactor = new StudentLikeInteractor(dao, dao, errorPresenter);
        interactor.execute(inputData);
    }

    @Test
    void testPostDifferentTime() {
        // Set up in-memory repository
        InMemoryUserDataAccessObject dao = new InMemoryUserDataAccessObject();

        // Create a club
        ClubFactory clubFactory = new ClubUserFactory();
        Club club = clubFactory.create("Photography Club", "photo@university.com", "password");
        club.setClubDescription("For photography enthusiasts.");

        // Create a student in the club
        StudentFactory studentFactory = new StudentUserFactory();
        Student student = studentFactory.create("Alice", "alice@university.com", "password");
        student.joinClub(club);
        club.addClubMember(student);

        PostFactory postFactory = new AnnouncementFactory();
        // Create a post for the club
        Post post = postFactory.create("Black and White Photo contest announcement.", "Details about the contest...");
        club.addClubPost(post);
        dao.saveClub(club);
        dao.savePost(post, club);
        dao.saveStudent(student);

        // Input Data with incorrect post date/time
        Map<String, Object> postData = new HashMap<>();
        postData.put("title", post.getTitle());
        postData.put("content", post.getContent());
        postData.put("likes", post.numberOfLikes());
        postData.put("dislikes", post.numberOfDislikes());
        postData.put("liked", post.getLikes().contains(student.getEmail()));
        postData.put("disliked", post.getDislikes().contains(student.getEmail()));
        postData.put("club-email", club.getEmail());
        postData.put("time", "12:00:00"); // Incorrect time
        postData.put("date", post.dateOfPosting()); // Correct date

        StudentLikeInputData inputData = new StudentLikeInputData(student.getEmail(), postData);

        // Output boundary for error verification
        StudentLikeOutputBoundary errorPresenter = new StudentLikeOutputBoundary() {
            @Override
            public void prepareSuccessView(StudentLikeOutputData data) {
                fail("Unexpected success call");
            }

            @Override
            public void prepareErrorView(String errorMessage) {
                assertEquals("post does not exist", errorMessage);
            }
        };

        // Interactor execution
        StudentLikeInteractor interactor = new StudentLikeInteractor(dao, dao, errorPresenter);
        interactor.execute(inputData);
    }
}
