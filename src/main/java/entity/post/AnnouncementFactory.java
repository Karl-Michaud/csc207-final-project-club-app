package entity.post;

import entity.data_structure.DataStoreArrays;

/**
 * Factory for creating new Announcement posts.
 */
public class AnnouncementFactory implements PostFactory {

    /**
     * Create a new Post.
     * @param title title of the post
     * @param content contents of the post.
     * @return the new post
     */
    public Announcement create(String title, String content) {
        return new Announcement(title, content, new DataStoreArrays<>(), new DataStoreArrays<>());
    }

    /**
     * Create a new Post.
     * @param title title of the post
     * @param content contents of the post.
     * @param userLiked users that have liked
     * @param userDisliked users that have disliked
     * @return the new post
     */
    public Announcement create(String title, String content, DataStoreArrays userLiked, DataStoreArrays userDisliked) {
        return new Announcement(title, content, userLiked, userDisliked);
    }
}
