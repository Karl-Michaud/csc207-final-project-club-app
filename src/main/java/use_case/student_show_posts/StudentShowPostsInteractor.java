package use_case.student_show_posts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entity.post.Post;
import entity.user.Club;
import entity.user.Student;

/**
 * Interactor for show posts use case.
 */
public class StudentShowPostsInteractor implements StudentShowPostsInputBoundary {
    private final StudentShowPostsOutputBoundary showPostsPresenter;
    private final StudentShowPostsClubAccessInterface studentShowPostsClubAccessInterface;
    private final StudentShowPostsStudentAccessInterface studentShowPostsStudentAccessInterface;

    public StudentShowPostsInteractor(StudentShowPostsStudentAccessInterface studentShowPostsStudentAccessInterface,
                                      StudentShowPostsClubAccessInterface studentShowPostsClubAccessInterface,
                                      StudentShowPostsOutputBoundary showPostsPresenter) {
        this.showPostsPresenter = showPostsPresenter;
        this.studentShowPostsClubAccessInterface = studentShowPostsClubAccessInterface;
        this.studentShowPostsStudentAccessInterface = studentShowPostsStudentAccessInterface;
    }

    @Override
    public void execute(StudentShowPostsInputData inputData) {
        final String currUserEmail = inputData.getUserEmail();
        if (!studentShowPostsStudentAccessInterface.existsByEmailStudent(currUserEmail)) {
            showPostsPresenter.prepareFailView("The account does not exist.");
        }
        else {
            // Get Student
            final Student currentUser = studentShowPostsStudentAccessInterface.getStudent(currUserEmail);

            // Get Joined clubs
            final ArrayList<Club> clubs = studentShowPostsStudentAccessInterface.getStudentJoinedClubs(currentUser);

            final Map<String, List<Map<String, Object>>> postData = new HashMap<>();
            for (final Club club : clubs) {
                final ArrayList<Map<String, Object>> clubPostData = new ArrayList<>();
                // Get posts for the club
                final ArrayList<Post> posts = studentShowPostsClubAccessInterface.getPosts(club);
                for (final Post post: posts) {
                    final Map<String, Object> singlePostData = new HashMap<>();
                    singlePostData.put("title", post.getTitle());
                    singlePostData.put("content", post.getContent());
                    singlePostData.put("likes", post.numberOfLikes());
                    singlePostData.put("dislikes", post.numberOfDislikes());
                    singlePostData.put("liked", post.getLikes().contains(currentUser.getEmail()));
                    singlePostData.put("disliked", post.getDislikes().contains(currentUser.getEmail()));
                    singlePostData.put("club-email", club.getEmail());
                    singlePostData.put("time", post.timeOfPosting());
                    singlePostData.put("date", post.dateOfPosting());
                    clubPostData.add(singlePostData);
                }
                postData.put(club.getUsername(), clubPostData);
            }
            final StudentShowPostsOutputData outputData = new StudentShowPostsOutputData(postData, currUserEmail);
            showPostsPresenter.preparePostContent(outputData);
        }
    }
}
