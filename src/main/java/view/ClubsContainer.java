package view;

import java.awt.*;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import interface_adapter.student_logged_in.explore_clubs.ExploreClubsController;

/**
 * A container which stores all the clubs the user has joined.
 */
public class ClubsContainer extends JPanel {

    public ClubsContainer(List<Map<String, String>> clubs, String studentEmail,
                          ExploreClubsController exploreClubsController) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setMaximumSize(new Dimension(300, Integer.MAX_VALUE));
        if (clubs == null || clubs.isEmpty()) {
            this.add(new JLabel("You currently don't follow any clubs."));
        }
        else {
            for (Map<String, String> club : clubs) {
                final ClubMiniPanel clubMiniPanel = new ClubMiniPanel(club, studentEmail);
                clubMiniPanel.setExploreClubsController(exploreClubsController);

                this.add(clubMiniPanel);
            }
        }
    }
}
