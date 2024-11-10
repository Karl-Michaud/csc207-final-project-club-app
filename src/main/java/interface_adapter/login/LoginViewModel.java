package interface_adapter.login;

import interface_adapter.ViewModel;

/**
 * The ViewModel for the Signup View.
 */
public class LoginViewModel extends ViewModel<LoginState> {

    public LoginViewModel() {
        super("login");
        setState(new LoginState());
    }



}