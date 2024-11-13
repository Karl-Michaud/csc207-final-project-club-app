package interface_adapter.login.club_login;

import interface_adapter.ViewManagerModel;
import interface_adapter.club_home.ClubHomeState;
import interface_adapter.club_home.ClubHomeViewModel;
import use_case.club_login.ClubLoginOutputBoundary;
import use_case.club_login.ClubLoginOutputData;

/**
 * The Presenter for the Club Login Use Case.
 */
public class ClubLoginPresenter implements ClubLoginOutputBoundary {

    private final ClubLoginViewModel clubLoginViewModel;
    private final ClubHomeViewModel homeViewModel;
    private final ViewManagerModel viewManagerModel;

    public ClubLoginPresenter(ViewManagerModel viewManagerModel,
                              ClubHomeViewModel clubHomeViewModel,
                              ClubLoginViewModel clubLoginViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.homeViewModel = clubHomeViewModel;
        this.clubLoginViewModel = clubLoginViewModel;
    }

    @Override
    public void prepareSuccessView(ClubLoginOutputData response) {
        //On success switch to the home view.
        setHomePageState(response);

        this.viewManagerModel.setState(homeViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }

    /**
     *  Helper method to prepare the home page view after logging in.
     * @param response the input data getting passed to the presenter
     */
    private void setHomePageState(ClubLoginOutputData response) {
        final ClubHomeState clubHomeState = homeViewModel.getState();
        clubHomeState.setUsername(response.getUsername());
        this.homeViewModel.setState(clubHomeState);
        this.homeViewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        final ClubLoginState clubLoginState = clubLoginViewModel.getState();
        clubLoginState.setLoginError(error);
        clubLoginViewModel.firePropertyChanged();
    }
}