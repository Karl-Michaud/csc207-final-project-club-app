package use_case.signup.club_signup;

import entity.user.Club;

/**
 * DAO for the Club Signup Use Case.
 */
public interface ClubSignupDataAccessInterface {

    /**
     * Checks if the given username exists.
     * @param username the username to look for
     * @return true if a user with the given username exists; false otherwise
     */
    boolean existsByNameClub(String username);

    /**
     * Checks if the given email exists.
     * @param email the email to look for
     * @return true if a user with the given email exists; false otherwise
     */
    boolean existsByEmailClub(String email);

    /**
     * Saves the club user.
     * @param user the club user to save
     */
    void saveClub(Club user);
}
