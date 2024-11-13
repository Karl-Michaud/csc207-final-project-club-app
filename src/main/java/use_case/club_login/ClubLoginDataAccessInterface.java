package use_case.club_login;

import entity.user.Club;

/**
 * Data access interface for the login use case for clubs.
 */
public interface ClubLoginDataAccessInterface {
    /**
     * Checks if the given email exists in the database.
     * @param email the email to look for
     * @return true if a Club with the given email exists in the database.
     */
    boolean existsByEmail(String email);

    /**
     * Returns the club with the given email.
     * @param email of the club we are looking for
     * @return the Club with the given email
     */
    Club getClub(String email);
}