package data_access;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import entity.post.Post;
import entity.user.Club;
import entity.user.Student;
import use_case.club_create_post.ClubCreatePostUserDataAccessInterface;
import use_case.club_get_members.ClubGetMembersUserDataAccessInterface;
import use_case.club_remove_member.ClubRemoveMemberClubDataAccessInterface;
import use_case.club_remove_member.ClubRemoveMemberStudentDataAccessInterface;
import use_case.login.club_login.ClubLoginDataAccessInterface;
import use_case.login.student_login.StudentLoginDataAccessInterface;
import use_case.signup.club_signup.ClubSignupUserDataAccessInterface;
import use_case.signup.student_signup.StudentSignupUserDataAccessInterface;
import use_case.student_join_club.StudentJoinClubAccessInterface;
import use_case.student_leave_club.StudentLeaveClubAccessInterface;
import use_case.student_search_club.StudentSearchClubAccessInterface;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * Persisting memory implementation of the DAO for storing user data.
 * This implementation uses Firebase and only persists data regarding the
 * Student entity
 */
public class ClubFirestoreUserDataAccessObject implements ClubCreatePostUserDataAccessInterface,
        ClubGetMembersUserDataAccessInterface, ClubRemoveMemberClubDataAccessInterface, ClubLoginDataAccessInterface,
        ClubSignupUserDataAccessInterface {
    private final Firestore db;
    private final String clubs = "clubs";
    private final String usernames = "username";

    public ClubFirestoreUserDataAccessObject() throws IOException {
        // TODO fix this to be environment variable
        final FileInputStream serviceAccount =
                new FileInputStream("/ServiceAccountKey.json");

        final FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        FirebaseApp.initializeApp(options);
        this.db = FirestoreClient.getFirestore();
    }

    @Override
    public boolean existsByNameClub(String username) {
        final ApiFuture<QuerySnapshot> future = db.collection(clubs).whereEqualTo(usernames, username).get();
        boolean returnValue;
        try {
            returnValue = !future.get().isEmpty();
        }
        catch (InterruptedException | ExecutionException ex) {
            // Handle exceptions appropriately
            ex.printStackTrace();
            returnValue = false;
        }
        return returnValue;
    }

    @Override
    public boolean existsByEmailClub(String email) {
        final DocumentReference docRef = db.collection(clubs).document(email);
        final ApiFuture<DocumentSnapshot> future = docRef.get();
        boolean returnValue;
        try {
            final DocumentSnapshot document = future.get();
            returnValue = document.exists();
        }
        catch (InterruptedException | ExecutionException ex) {
            // Handle exceptions appropriately
            ex.printStackTrace();
            returnValue = false;
        }
        return returnValue;
    }

    @Override
    public void saveClub(Club user) {
        final DocumentReference docRef = db.collection(clubs).document(user.getEmail());
        final ApiFuture<WriteResult> writeResult = docRef.set(user);
        try {
            System.out.println("Update time : " + writeResult.get().getUpdateTime());
        }
        catch (InterruptedException | ExecutionException ex) {
            // Handle exceptions appropriately
            ex.printStackTrace();
        }
    }

    @Override
    public void savePost(Post post, Club club) {
        // same implementation as saveClub
        // method overwrites the club data including the new post.
        final DocumentReference docRef = db.collection(clubs).document(club.getEmail());
        final ApiFuture<WriteResult> writeResult = docRef.set(club);
        try {
            System.out.println("Update time : " + writeResult.get().getUpdateTime());
        }
        catch (InterruptedException | ExecutionException ex) {
            // Handle exceptions appropriately
            ex.printStackTrace();
        }
    }

    @Override
    public Club getClub(String email) {
        // get student through email
        final DocumentReference docRef = db.collection(clubs).document(email);
        final ApiFuture<DocumentSnapshot> future = docRef.get();
        Club returnValue = null;
        try {
            final DocumentSnapshot document = future.get();
            if (document.exists()) {
                returnValue = document.toObject(Club.class);
            }
        }
        catch (InterruptedException | ExecutionException ex) {
            ex.printStackTrace();
        }
        return returnValue;
    }

    @Override
    public void updateClubMembers(Club club) {
        // same implementation as saveClub
        // method overwrites the club data including the updated student list.
        final DocumentReference docRef = db.collection(clubs).document(club.getEmail());
        final ApiFuture<WriteResult> writeResult = docRef.set(club);
        try {
            System.out.println("Update time: " + writeResult.get().getUpdateTime());
        }
        catch (InterruptedException | ExecutionException ex) {
            // Handle exceptions appropriately
            ex.printStackTrace();
        }
    }
}
