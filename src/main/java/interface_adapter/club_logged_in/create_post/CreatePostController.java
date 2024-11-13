package interface_adapter.club_logged_in.create_post;

import use_case.club_create_post.ClubCreatePostInputBoundary;
import use_case.club_create_post.ClubCreatePostInputData;

/**
 * Controller for the login use case.
 */
public class CreatePostController {
    private final ClubCreatePostInputBoundary createPostInteractor;

    public CreatePostController(ClubCreatePostInputBoundary createPostInteractor) {
        this.createPostInteractor = createPostInteractor;
    }

    /**
     * Executes the club create post use case.
     * @param email of the club crating the post
     * @param title of the post
     * @param content of the post
     */
    public void execute(String email, String title, String content) {
        final ClubCreatePostInputData createPostInputData = new ClubCreatePostInputData(email, title, content);
        createPostInteractor.execute(createPostInputData);
    }
}
