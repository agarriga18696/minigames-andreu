package cat.andreu.jovia.games.coloring;

import cat.andreu.jovia.games.GameEngine;
import cat.andreu.jovia.games.GamePackage;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;

/**
 * Game engine responsible for running coloring games.
 *
 * @author Andreu
 * @version 1.0
 */
public final class ColoringGameEngine implements GameEngine {

    /**
     * Identifier used by coloring game packages.
     */
    public static final String TYPE = "coloring";

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public JPanel createView(
            GamePackage gamePackage
    ) {
        JPanel panel =
                new JPanel(
                        new BorderLayout()
                );

        panel.add(
                new JLabel(
                        gamePackage.id(),
                        JLabel.CENTER
                ),
                BorderLayout.CENTER
        );

        return panel;
    }
}