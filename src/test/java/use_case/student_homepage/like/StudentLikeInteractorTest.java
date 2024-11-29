package use_case.student_homepage.like;

import data_access.InMemoryUserDataAccessObject;
import entity.data_structure.DataStore;
import entity.data_structure.DataStoreArrays;
import entity.post.Announcement;
import entity.post.AnnouncementFactory;
import entity.post.Post;
import entity.post.PostFactory;
import entity.user.Club;
import entity.user.Student;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class StudentLikeInteractorTest {
    @Test
    void successLikedTest() {
        // Set up in-memory repository
        InMemoryUserDataAccessObject dao = new InMemoryUserDataAccessObject();

        // Create a club
        DataStore<Student> students1 = new DataStoreArrays<Student>();
        Club club = new Club("Photography Club", "photo@university.com", "password", students1, new DataStoreArrays<>());
        club.setClubDescription("For photography enthusiasts.");

        // Create a student in the club
        DataStore<Club> joinedClubs = new DataStoreArrays<>();
        joinedClubs.add(club);
        Student student = new Student("Alice", "alice@university.com", "password", joinedClubs);
        club.addClubMember(student);


        PostFactory postFactory = new AnnouncementFactory();
        // Create a post for the club
        Post post = postFactory.create("Black and White Photo contest announcement.", "We're planning to host"
                + "a photo contest around the theme black and white photos and would like to hear your feedback!" +
                " Get creative," + "ditch the colors, and win prizes!");

        club.addClubPost(post);
        dao.saveClub(club);
        dao.saveStudent(student);
        // Input Data
        Map<String, Object> postData = new HashMap<String, Object>();
        postData.put("title", post.getTitle());
        postData.put("content", post.getContent());
        postData.put("likes", post.numberOfLikes());
        postData.put("dislikes", post.numberOfDislikes());
        postData.put("liked", post.getLikes().contains(student));
        postData.put("disliked", post.getDislikes().contains(student));
        postData.put("club-email", club.getEmail());
        postData.put("time", post.timeOfPosting());
        postData.put("date", post.dateOfPosting());
        StudentLikeInputData inputData = new StudentLikeInputData(student.getEmail(), postData);

        // Output boundary for success verification
        StudentLikeOutputBoundary successPresenter = new StudentLikeOutputBoundary() {
            @Override
            public void prepareSuccessView(StudentLikeOutputData data) {
                assertEquals(postData, data.getPostData());
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
        DataStore<Student> students1 = new DataStoreArrays<Student>();
        Club club = new Club("Photography Club", "photo@university.com", "password", students1, new DataStoreArrays<>());
        club.setClubDescription("For photography enthusiasts.");

        // Create a student in the club
        DataStore<Club> joinedClubs = new DataStoreArrays<>();
        joinedClubs.add(club);
        Student student = new Student("Alice", "alice@university.com", "password", joinedClubs);
        club.addClubMember(student);


        PostFactory postFactory = new AnnouncementFactory();
        // Create a post for the club
        Post post = postFactory.create("Black and White Photo contest announcement.", "We're planning to host"
                + "a photo contest around the theme black and white photos and would like to hear your feedback!" +
                " Get creative," + "ditch the colors, and win prizes!");
        post.addLike(student);
        club.addClubPost(post);
        dao.saveClub(club);
        dao.saveStudent(student);
        // Input Data
        Map<String, Object> postData = new HashMap<String, Object>();
        postData.put("title", post.getTitle());
        postData.put("content", post.getContent());
        postData.put("likes", post.numberOfLikes());
        postData.put("dislikes", post.numberOfDislikes());
        postData.put("liked", post.getLikes().contains(student));
        postData.put("disliked", post.getDislikes().contains(student));
        postData.put("club-email", club.getEmail());
        postData.put("time", post.timeOfPosting());
        postData.put("date", post.dateOfPosting());
        StudentLikeInputData inputData = new StudentLikeInputData(student.getEmail(), postData);

        // Output boundary for success verification
        StudentLikeOutputBoundary successPresenter = new StudentLikeOutputBoundary() {
            @Override
            public void prepareSuccessView(StudentLikeOutputData data) {
                assertEquals(postData, data.getPostData());
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
}