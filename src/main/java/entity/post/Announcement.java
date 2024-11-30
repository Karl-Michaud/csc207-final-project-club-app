package entity.post;

import java.time.LocalDate;
import java.time.LocalTime;

import entity.data_structure.DataStore;
import entity.data_structure.DataStoreArrays;
import entity.user.User;

/**
 * Announcement class that implements the Post interface. Announcement is a type of post.
 * For now, announcement is the only type of Post.
 */
public class Announcement implements Post {
    // Text information of post
    private String title;
    private String content;

    // Date/time information of post
    private LocalTime timeOfPosting;
    private LocalDate dateOfPosting;

    // Like/Dislike information of post
    private DataStoreArrays<User> likes;
    private DataStoreArrays<User> dislikes;

    public Announcement() {
        this.likes = new DataStoreArrays<>();
        this.dislikes = new DataStoreArrays<>();
        this.dateOfPosting = LocalDate.now();
        this.timeOfPosting = LocalTime.now();
        this.title = "";
        this.content = "";
    }

    public Announcement(String title, String content) {
        this.title = title;
        this.content = content;
        this.likes = new DataStoreArrays<>();
        this.dislikes = new DataStoreArrays<>();
        this.timeOfPosting = LocalTime.now();
        this.dateOfPosting = LocalDate.now();
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    /**
     * Returns the date of publication of the post.
     * @return String the date of publication of the post
     */
    public LocalDate dateOfPosting() {
        return dateOfPosting;
    }

    /**
     * Returns the time of publication of the post.
     * @return String the time of the publication of the post
     */
    public LocalTime timeOfPosting() {
        return timeOfPosting;
    }

    /**
     * Returns the number of likes.
     * @return int number of likes.
     */
    public int numberOfLikes() {
        return likes.size();
    }

    /**
     * Number of dislikes for given post.
     * @return int the number of dislikes
     */
    public int numberOfDislikes() {
        return dislikes.size();
    }

    @Override
    public DataStore<User> getLikes() {
        return this.likes;
    }

    @Override
    public DataStore<User> getDislikes() {
        return this.dislikes;
    }

    /**
     * User likes the post.
     * @param user user that likes the post.
     */
    public void addLike(User user) {
        likes.add(user);
    }

    /**
     * User unlikes the post.
     * @param user that unlikes the post
     */
    public void removeLike(User user) {
        likes.remove(user);
    }

    /**
     * User dislikes the post.
     * @param user that dislikes the post
     */
    public void addDislike(User user) {
        dislikes.add(user);
    }

    /**
     * User removes dislike from post.
     * @param user that wants to remove dislike
     */
    public void removeDislike(User user) {
        dislikes.remove(user);
    }

}
