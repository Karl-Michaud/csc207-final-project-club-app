package entity.post;

import entity.user.User;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

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
    private Map<Integer, User> likes;
    private Map<Integer, User> dislikes;

    // Post Identification
    private int postID;

    public Announcement(String title, String content) {
        this.title = title;
        this.content = content;

        this.timeOfPosting = LocalTime.now();
        this.dateOfPosting = LocalDate.now();

        this.postID = -1;
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
    public String dateOfPosting() {
        return dateOfPosting.toString();
    }

    /**
     * Returns the time of publication of the post.
     * @return String the time of the publication of the post
     */
    public String timeOfPosting() {
        return timeOfPosting.toString();
    }

    public int getPostID() {
        return postID;
    }

    public void setPostID(int postID) {
        this.postID = postID;
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

    /**
     * User likes the post.
     * @param user user that likes the post.
     */
    public void addLike(User user) {
        likes.put(user.getUserID(), user);
    }

    /**
     * User unlikes the post.
     * @param user that unlikes the post
     */
    public void removeLike(User user) {
        likes.remove(user.getUserID());
    }

    /**
     * User dislikes the post.
     * @param user that dislikes the post
     */
    public void addDislike(User user) {
        dislikes.put(user.getUserID(), user);
    }

    /**
     * User removes dislike from post.
     * @param user that wants to remove dislike
     */
    public void removeDislike(User user) {
        dislikes.remove(user.getUserID());
    }

}