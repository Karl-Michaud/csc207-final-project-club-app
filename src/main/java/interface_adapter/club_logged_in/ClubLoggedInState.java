package interface_adapter.club_logged_in;

import entity.data_structure.DataStore;
import entity.post.Post;
import entity.user.Student;

/**
 * Club Logged In state.
 */
public class ClubLoggedInState {
    private String clubName = "";

    private String email = "";

    private String password = "";
    private String message;

    private String descriptionTextArea = "";

    private DataStore<Post> posts;
    private DataStore<Student> members;

    public ClubLoggedInState() {
        // empty body since we have a constructor already and so no default constructor anymore.
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDescriptionTextArea() {
        return descriptionTextArea;
    }

    public void setDescriptionTextArea(String descriptionTextArea) {
        this.descriptionTextArea = descriptionTextArea;
    }

    public DataStore<Post> getPosts() {
        return posts;
    }

    public void setPosts(DataStore<Post> posts) {
        this.posts = posts;
    }

    public DataStore<Student> getMembers() {
        return members;
    }

    public void setMembers(DataStore<Student> members) {
        this.members = members;
    }
}
