package interface_adapter.signup.student_signup;

import interface_adapter.ViewManagerModel;
import interface_adapter.login.LoginState;
import interface_adapter.login.LoginViewModel;
import use_case.signup.student_signup.StudentSignupOutputBoundary;
import use_case.signup.student_signup.StudentSignupOutputData;

/**
 * The Presenter for the Signup Use Case.
 */
public class StudentSignupPresenter implements StudentSignupOutputBoundary {

    private final StudentSignupViewModel studentSignupViewModel;
    private final LoginViewModel loginViewModel;
    private final ViewManagerModel viewManagerModel;

    public StudentSignupPresenter(ViewManagerModel viewManagerModel,
                                  StudentSignupViewModel studentSignupViewModel,
                                  LoginViewModel loginViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.studentSignupViewModel = studentSignupViewModel;
        this.loginViewModel = loginViewModel;
    }

    @Override
    public void prepareSuccessView(StudentSignupOutputData response) {
        // Gets the login state and sets the email field to the newly registered email
        final LoginState loginState = loginViewModel.getState();
        loginState.setIdentifier(response.getEmail());
        this.loginViewModel.setState(loginState);
        loginViewModel.firePropertyChanged();

        // Switch to the login view
        viewManagerModel.setState(loginViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        // Sets the signup state to have an error message and fires a property change to display the message
        final StudentSignupState studentSignupState = studentSignupViewModel.getState();
        studentSignupState.setSignupError(error);
        studentSignupViewModel.firePropertyChanged();
    }

    @Override
    public void switchToLoginView() {
        // Clears the LoginState
        final LoginState state = loginViewModel.getState();
        state.setLoginError(null);
        state.setPassword("");
        state.setIdentifier("");
        loginViewModel.setState(state);
        loginViewModel.firePropertyChanged();

        // Switch to the login view
        viewManagerModel.setState(loginViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}
