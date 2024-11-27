package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import interface_adapter.club_get_posts.ClubGetPostsController;
import interface_adapter.club_logged_in.ClubLoggedInState;
import interface_adapter.club_logged_in.ClubLoggedInViewModel;
import interface_adapter.club_logged_in.club_create_post.ClubCreatePostController;
import interface_adapter.club_logged_in.club_get_members.ClubGetMembersController;
import interface_adapter.club_logged_in.club_remove_member.ClubRemoveMemberController;
import interface_adapter.club_update_desc.ClubUpdateDescController;
import interface_adapter.logout.LogoutController;

/**
 * The Club Logged View.
 */
public class ClubLoggedInView extends JPanel implements PropertyChangeListener {
    private JPanel panelClubHome;
    private JPanel membersPanel;
    private JPanel postsPanel;
    private JPanel descriptionPanel;
    private JPanel logoPanel;
    private JPanel optionsPanel;
    private JButton updateDescriptionButton;
    private JButton logoutButton;
    private JButton refreshButton;
    private JButton createPostButton;
    private JLabel logoLabel;
    private JLabel message;
    private JScrollPane membersScrollPane;
    private JScrollPane descScrollPane;
    private JScrollPane postsScrollPane;
    private JTextArea descriptionTextArea;

    private final String viewName = "club logged in";
    private final ClubLoggedInViewModel clubLoggedInViewModel;

    private ClubCreatePostController clubCreatePostController;
    private LogoutController logoutController;
    private ClubGetPostsController clubGetPostsController;
    private ClubGetMembersController clubGetMembersController;
    private ClubUpdateDescController clubUpdateDescController;
    private ClubRemoveMemberController clubRemoveMemberController;

    public ClubLoggedInView(ClubLoggedInViewModel clubLoggedInViewModel) {
        this.clubLoggedInViewModel = clubLoggedInViewModel;
        this.clubLoggedInViewModel.addPropertyChangeListener(this);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(panelClubHome);

        createPostButton.addActionListener(
                // This creates an anonymous subclass of ActionListener and instantiates it.
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(createPostButton)) {
                            clubCreatePostController.switchToCreatePostView();
                        }
                    }
                }
        );

        logoutButton.addActionListener(
                // This creates an anonymous subclass of ActionListener and instantiates it.
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(logoutButton)) {
                            // Executes the Logout use case.
                            logoutController.execute();
                        }
                    }
                }
        );

        refreshButton.addActionListener(
                // This creates an anonymous subclass of ActionListener and instantiates it.
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(refreshButton)) {
                            // Executes both the Get Posts and Get Members usecase to get an updated version of them
                            final ClubLoggedInState currentState = clubLoggedInViewModel.getState();
                            clubGetPostsController.execute(currentState.getEmail());
                            clubGetMembersController.execute(currentState.getEmail());
                        }
                    }
                }
        );

        updateDescriptionButton.addActionListener(
                // This creates an anonymous subclass of ActionListener and instantiates it.
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(updateDescriptionButton)) {
                            // Gets the current view state containing the new description in the TextArea
                            final ClubLoggedInState currentState = clubLoggedInViewModel.getState();

                            // Executes the update description usecase
                            clubUpdateDescController.execute(currentState.getEmail(),
                                    currentState.getDescriptionTextArea());
                        }
                    }
                }
        );

        addDescriptionTextAreaListener();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("state")) {
            final ClubLoggedInState state = (ClubLoggedInState) evt.getNewValue();
            clubGetMembersController.execute(state.getEmail());
            clubGetPostsController.execute(state.getEmail());
        }
        else if (evt.getPropertyName().equals("create post")) {
            final ClubLoggedInState state = (ClubLoggedInState) evt.getNewValue();
            // Executes the get posts use case and gets the updated state
            clubGetPostsController.execute(state.getEmail());
            final ClubLoggedInState updatedState = clubLoggedInViewModel.getState();

            // Creates a PostTextPanel for every post retrieved and adds it to this view
            final List<String> postTitles = updatedState.getPostTitles();
            final List<String> postBodies = updatedState.getPostBodies();

            for (int i = 0; i < postTitles.size(); i++) {
                final PostTextPanel postPanel = new PostTextPanel(postTitles.get(i), postBodies.get(i));
                postsScrollPane.add(postPanel);
            }
        }
        else if (evt.getPropertyName().equals("get members")) {
            final ClubLoggedInState state = (ClubLoggedInState) evt.getNewValue();
            // Executes the get members use case and gets the updated state
            clubGetMembersController.execute(state.getEmail());
            final ClubLoggedInState updatedState = clubLoggedInViewModel.getState();

            // Creates a RemoveMemberPanel for every member retrieved and adds it to this view
            final List<String> memberNames = updatedState.getMembersName();
            final List<String> memberEmails = updatedState.getMembersEmail();

            for (int i = 0; i < memberNames.size(); i++) {
                final RemoveMemberPanel memberPanel = new RemoveMemberPanel(clubRemoveMemberController,
                        updatedState.getEmail(), memberEmails.get(i), memberNames.get(i));
                membersScrollPane.add(memberPanel);
            }
        }
        else if (evt.getPropertyName().equals("reload message")) {
            // Sets the message JLabel to have the text in the current state.
            final ClubLoggedInState state = (ClubLoggedInState) evt.getNewValue();
            message.setText(state.getMessage());
        }
    }

    /**
     * Adds a listener to the descriptionTextArea to update the club logged in state.
     */
    private void addDescriptionTextAreaListener() {
        descriptionTextArea.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final ClubLoggedInState currentState = clubLoggedInViewModel.getState();
                currentState.setDescriptionTextArea(descriptionTextArea.getText());
                clubLoggedInViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                documentListenerHelper();
            }
        });
    }

    public void setClubCreatePostController(ClubCreatePostController controller) {
        this.clubCreatePostController = controller;
    }

    public void setLogoutController(LogoutController logoutController) {
        this.logoutController = logoutController;
    }

    public void setClubGetPostsController(ClubGetPostsController controller) {
        this.clubGetPostsController = controller;
    }

    public void setClubGetMembersController(ClubGetMembersController controller) {
        this.clubGetMembersController = controller;
    }

    public void setClubUpdateDescController(ClubUpdateDescController controller) {
        this.clubUpdateDescController = controller;
    }

    public void setClubRemoveMemberController(ClubRemoveMemberController controller) {
        this.clubRemoveMemberController = controller;
    }

    public String getViewName() {
        return viewName;
    }
}