package interface_adapter.club_get_posts;

import interface_adapter.ViewManagerModel;
import interface_adapter.club_logged_in.ClubLoggedInState;
import interface_adapter.club_logged_in.ClubLoggedInViewModel;
import use_case.club_get_posts.ClubGetPostsOutputBoundary;
import use_case.club_get_posts.ClubGetPostsOutputData;

/**
 * The presenter for the Club Get Posts Use Case.
 */
public class ClubGetPostsPresenter implements ClubGetPostsOutputBoundary {

    private final ClubLoggedInViewModel clubLoggedInViewModel;
    private final ViewManagerModel viewManagerModel;

    public ClubGetPostsPresenter(ClubLoggedInViewModel clubLoggedInViewModel, ViewManagerModel viewManagerModel) {
        this.clubLoggedInViewModel = clubLoggedInViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareDisplayPosts(ClubGetPostsOutputData outputData) {
        // Get the state of the current ClubLoggedInViewModel and set the message and clubPosts to the new ones
        final ClubLoggedInState clubLoggedInState = clubLoggedInViewModel.getState();
        clubLoggedInState.setMessage(outputData.getMessage());
        clubLoggedInState.setPostTitles(outputData.getPostTitles());
        clubLoggedInState.setPostBodies(outputData.getPostBodies());
        clubLoggedInViewModel.setState(clubLoggedInState);
        clubLoggedInViewModel.firePropertyChanged("reload posts");
    }

    @Override
    public void prepareFailMessage(ClubGetPostsOutputData outputData) {
        // Get the state of the current ClubLoggedInViewModel and set the message to the new one
        final ClubLoggedInState clubLoggedInState = clubLoggedInViewModel.getState();
        clubLoggedInState.setMessage(outputData.getMessage());
        clubLoggedInViewModel.setState(clubLoggedInState);
        clubLoggedInViewModel.firePropertyChanged("reload message");
    }
}
