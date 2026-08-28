package cat.andreu.jovia;

import javax.swing.UIManager;

/**
 * Entry point for Jovia.
 *
 * @author Andreu
 * @version 2.0
 */
public class Main {

    private Main() {
        // Entry-point class
    }

    /**
     * Starts the application using the system Look and Feel.
     */
    public static void main() {
        new JoviaApplication()
                .runWithLookAndFeel(
                        UIManager.getSystemLookAndFeelClassName()
                );
    }

}
