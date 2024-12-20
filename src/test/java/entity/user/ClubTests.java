package entity.user;

import entity.data_structure.DataStore;
import entity.data_structure.DataStoreArrays;
import entity.post.AnnouncementFactory;
import entity.post.Post;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Tests for the Club entity.
 */
public class ClubTests {
    static String clubName = "Test Club Name";
    static String clubEmail = "club@email.com";
    static String clubPassword = "TestClubPassword123";

    @Test
    public void testClubCreation() {
        // Create a club factory
        final ClubFactory clubFactory = new ClubUserFactory();

        // Create a test club
        Club testClub = clubFactory.create(clubName, clubEmail, clubPassword);

        // verify that all the data matches
        assertNotNull(testClub);
        assertEquals(testClub.getUsername(), clubName);
        assertEquals(testClub.getEmail(), clubEmail);
        assertEquals(testClub.getPassword(), clubPassword);
        assertEquals("", testClub.getClubDescription());

        // Since it is a new club, there should be no posts and not members
        int sizeMembersEmails = testClub.getClubMembersEmails().size();
        assertEquals(0, sizeMembersEmails);

        int sizeMembersNames = testClub.getClubMembersNames().size();
        assertEquals(0, sizeMembersNames);

        int sizePostTitles = testClub.getClubPostsTitle().size();
        assertEquals(0, sizePostTitles);

        int sizePostDescriptions = testClub.getClubPostsDescription().size();
        assertEquals(0, sizePostDescriptions);

        testClub.setClubDescription("new");
        assertEquals("new", testClub.getClubDescription());
    }

    @Test
    public void testClubCreationMoreArguments() {
        // Create a club factory
        final ClubFactory clubFactory = new ClubUserFactory();
        final DataStore<String> clubMemberEmails = new DataStoreArrays<String>();
        final DataStore<String> clubMemberNames = new DataStoreArrays<String>();
        final DataStore<String> clubPostTitle = new DataStoreArrays<String>();
        final DataStore<String> clubPostDescription = new DataStoreArrays<String>();


        // Create a test club
        Club testClub = clubFactory.create(clubName, clubEmail, clubPassword, clubMemberEmails,
                clubMemberNames, clubPostTitle, clubPostDescription);

        // verify that all the data matches
        assertNotNull(testClub);
        assertEquals(testClub.getUsername(), clubName);
        assertEquals(testClub.getEmail(), clubEmail);
        assertEquals(testClub.getPassword(), clubPassword);
        assertEquals("", testClub.getClubDescription());

        // Since it is a new club, there should be no posts and not members
        int sizeMembersEmails = testClub.getClubMembersEmails().size();
        assertEquals(0, sizeMembersEmails);

        int sizeMembersNames = testClub.getClubMembersNames().size();
        assertEquals(0, sizeMembersNames);

        int sizePostTitles = testClub.getClubPostsTitle().size();
        assertEquals(0, sizePostTitles);

        int sizePostDescriptions = testClub.getClubPostsDescription().size();
        assertEquals(0, sizePostDescriptions);

        testClub.setClubDescription("new");
        assertEquals("new", testClub.getClubDescription());
    }

    @Test
    public void testClubMemberManipulation() {
        // Initialize the club factory
        final ClubFactory clubFactory = new ClubUserFactory();

        // Create a test club
        Club testClub = clubFactory.create(clubName, clubEmail, clubPassword);

        // Create and add 10 members
        StudentFactory studentFactory = new StudentUserFactory();
        ArrayList<Student> tracker = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Student student = studentFactory.create("members" + String.valueOf(i),
                    "members@" + String.valueOf(i), "123123123");
            testClub.addClubMember(student);
            tracker.add(student);
        }
        int sizeMembersEmails = testClub.getClubMembersEmails().size();
        int sizeMembersNames = testClub.getClubMembersNames().size();
        assertEquals(10, sizeMembersEmails);
        assertEquals(10, sizeMembersNames);

        // Check that the name match and remove the members
        for (int i = 0; i < 10; i++) {
            assertEquals(testClub.getClubMembersEmails().getByIndex(i), "members@" + String.valueOf(i));
            assertEquals(testClub.getClubMembersNames().getByIndex(i), "members" + String.valueOf(i));
        }

        // Remove all members
        for (int i = 0; i < 10; i++) {
            testClub.removeClubMember(tracker.get(i));
        }
        int sizeAfterRemoveEmails = testClub.getClubMembersEmails().size();
        assertEquals(0, sizeAfterRemoveEmails);

        int sizeAfterRemoveName = testClub.getClubMembersNames().size();
        assertEquals(0, sizeAfterRemoveName);
    }

    @Test
    public void testClubPostManipulation() {
        // Initialize the club factory
        final ClubFactory clubFactory = new ClubUserFactory();

        // Create a test club
        Club testClub = clubFactory.create(clubName, clubEmail, clubPassword);

        // Create and add 10 posts
        AnnouncementFactory announcementFactory = new AnnouncementFactory();
        ArrayList<Post> tracker = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Post post = announcementFactory.create("post" + String.valueOf(i),
                    "content" + String.valueOf(i));
            testClub.addClubPost(post);
            tracker.add(post);
        }

        int sizePostTitles = testClub.getClubPostsTitle().size();
        int sizePostDescriptions = testClub.getClubPostsDescription().size();
        assertEquals(10, sizePostTitles);
        assertEquals(10, sizePostDescriptions);

        // Check that the name match and remove the members
        for (int i = 0; i < 10; i++) {
            assertEquals(testClub.getClubPostsTitle().getByIndex(i), "post" + String.valueOf(i));
            assertEquals(testClub.getClubPostsDescription().getByIndex(i), "content" + String.valueOf(i));
            testClub.removeClubPost(tracker.get(i));
        }
    }
}
