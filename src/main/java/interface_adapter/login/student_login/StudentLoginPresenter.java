package interface_adapter.login.student_login;

import interface_adapter.ViewManagerModel;
import interface_adapter.login.LoginState;
import interface_adapter.login.LoginViewModel;
import interface_adapter.signup.student_signup.StudentSignupState;
import interface_adapter.signup.student_signup.StudentSignupViewModel;
import interface_adapter.student_home.StudentHomeState;
import interface_adapter.student_home.StudentHomeViewModel;
import use_case.login.student_login.StudentLoginOutputBoundary;
import use_case.login.student_login.StudentLoginOutputData;

/**
 * The Presenter for the Student Login Use Case.
 */
public class StudentLoginPresenter implements StudentLoginOutputBoundary {

    private final LoginViewModel loginViewModel;
    private final StudentHomeViewModel studentHomeViewModel;
    private final StudentSignupViewModel studentSignupViewModel;
    private final ViewManagerModel viewManagerModel;

    public StudentLoginPresenter(ViewManagerModel viewManagerModel,
                                 StudentHomeViewModel studentHomeViewModel,
                                 StudentSignupViewModel studentSignupViewModel,
                                 LoginViewModel loginViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.studentHomeViewModel = studentHomeViewModel;
        this.studentSignupViewModel = studentSignupViewModel;
        this.loginViewModel = loginViewModel;
    }

    @Override
    public void prepareSuccessView(StudentLoginOutputData response) {
        // On success switch to the home view.
        setHomePageState(response);

        this.viewManagerModel.setState(studentHomeViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }

    /**
     *  Helper method to prepare the home page view after logging in.
     * @param response the input data getting passed to the presenter
     */
    private void setHomePageState(StudentLoginOutputData response) {
        final StudentHomeState studentHomeState = studentHomeViewModel.getState();
        studentHomeState.setCurrentUser(response.getEmail());
        studentHomeState.setUsername(response.getUsername());
        this.studentHomeViewModel.setState(studentHomeState);
        this.studentHomeViewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        final LoginState loginState = loginViewModel.getState();
        loginState.setLoginError(error);
        loginViewModel.firePropertyChanged();
    }

    @Override
    public void switchToStudentSignupView() {
        // Clears the StudentSignupState
        final StudentSignupState state = studentSignupViewModel.getState();
        state.setEmail("");
        state.setPassword("");
        state.setUsername("");
        state.setRepeatPassword("");
        state.setSignupError(null);
        this.studentSignupViewModel.setState(state);
        this.studentSignupViewModel.firePropertyChanged();

        // Switches to the student signup view
        viewManagerModel.setState(studentSignupViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}
