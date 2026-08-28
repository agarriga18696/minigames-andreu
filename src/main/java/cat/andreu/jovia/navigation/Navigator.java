package cat.andreu.jovia.navigation;

import javax.swing.JPanel;

import java.awt.CardLayout;

/**
 * Manages navigation between application screens using a {@link CardLayout}.
 *
 * @author Andreu
 * @version 2.0
 */
public final class Navigator {

    // ----------------------------------------
    // ATTRIBUTES
    // ----------------------------------------

    private final CardLayout cardLayout;
    private final JPanel containerPanel;

    // ----------------------------------------
    // CONSTRUCTOR
    // ----------------------------------------

    /**
     * Creates an empty application navigator.
     */
    public Navigator() {
        cardLayout = new CardLayout();
        containerPanel = new JPanel(cardLayout);
    }

    // ----------------------------------------
    // NAVIGATION
    // ----------------------------------------

    /**
     * Returns the panel containing all registered screens.
     *
     * @return Navigation container.
     */
    public JPanel getContainer() {
        return containerPanel;
    }

    /**
     * Registers a screen with the specified identifier.
     *
     * @param name  Screen identifier.
     * @param panel Screen panel.
     */
    public void addScreen(
            String name,
            JPanel panel
    ) {

        containerPanel.add(
                panel,
                name
        );
    }

    /**
     * Displays the screen associated with the specified identifier.
     *
     * @param name Screen identifier.
     */
    public void show(
            String name
    ) {

        cardLayout.show(
                containerPanel,
                name
        );
    }
}