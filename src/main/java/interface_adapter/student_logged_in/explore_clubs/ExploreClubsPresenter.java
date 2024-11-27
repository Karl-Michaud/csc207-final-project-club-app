package interface_adapter.student_logged_in.explore_clubs;

import java.util.Map;

import interface_adapter.ViewManagerModel;
import interface_adapter.student_home.StudentHomeViewModel;
import use_case.explore_clubs.ExploreClubsOutputBoundary;
import use_case.explore_clubs.ExploreClubsOutputData;

/**
 * The Presenter for the Explore Clubs Use Case.
 */
public class ExploreClubsPresenter implements ExploreClubsOutputBoundary {

    private final ExploreClubsViewModel exploreClubsViewModel;
    private final ViewManagerModel viewManagerModel;
    private final StudentHomeViewModel studentHomeViewModel;

    public ExploreClubsPresenter(ViewManagerModel viewManagerModel, ExploreClubsViewModel exploreClubsViewModel,
                                 StudentHomeViewModel studentHomeViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.exploreClubsViewModel = exploreClubsViewModel;
        this.studentHomeViewModel = studentHomeViewModel;
    }

    @Override
    public void prepareSuccessView(ExploreClubsOutputData data) {
        // Update the ExploreClubsState with clubs and clear errors
        final ExploreClubsState state = exploreClubsViewModel.getState();
        state.setStudentEmail(data.getStudentEmail());
        state.setClubValues(data.getNotJoinedClubs());
        state.setJoinedClubEmails(data.getJoinedClubsEmails());
        state.setClubEmail(null);
        state.setError(null);

        exploreClubsViewModel.setState(state);
        exploreClubsViewModel.firePropertyChanged();

        // Set the ViewManager to the ExploreClubs view
        viewManagerModel.setState(exploreClubsViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        // Update the ExploreClubsState with the error message
        final ExploreClubsState state = exploreClubsViewModel.getState();
        state.setError(errorMessage);

        exploreClubsViewModel.setState(state);
        exploreClubsViewModel.firePropertyChanged();
    }

    @Override
    public void switchToClubView(Map<String, String> club) {
        // Set the selected club in the state
        final ExploreClubsState state = exploreClubsViewModel.getState();
        state.setClubEmail(club.get("email"));

        exploreClubsViewModel.setState(state);
        exploreClubsViewModel.firePropertyChanged("clubEmail");

        // Transition the ViewManager to the Club Description view
        viewManagerModel.setState("ClubDescriptionView");
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void switchToHomeView() {
        // Transition the ViewManager to the Home view
        final ExploreClubsState state = exploreClubsViewModel.getState();
        state.setStudentEmail(null);
        state.setClubEmail(null);
        // set and fire the states
        exploreClubsViewModel.setState(state);
        exploreClubsViewModel.firePropertyChanged();
        viewManagerModel.setState(studentHomeViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}
