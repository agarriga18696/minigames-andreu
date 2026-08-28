package cat.andreu.jovia.view;

import cat.andreu.jovia.i18n.Messages;

import io.github.agarriga18696.andreuutils.swing.ComponentsSwing;
import io.github.agarriga18696.andreuutils.swing.IconsFatCow;
import io.github.agarriga18696.andreuutils.swing.IconsSwing;
import io.github.agarriga18696.andreuutils.swing.PanelsSwing;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;

/**
 * Represents the application home panel.
 *
 * @author Andreu
 * @version 2.0
 */
public final class HomePanel extends JPanel {

    // ----------------------------------------
    // ATTRIBUTES
    // ----------------------------------------

    private final JLabel titleLabel;
    private final JButton coloringButton;

    // ----------------------------------------
    // CONSTRUCTOR
    // ----------------------------------------

    /**
     * Creates the home panel.
     */
    public HomePanel() {
        setLayout(
                new BorderLayout(
                        20,
                        20
                )
        );

        setBorder(
                BorderFactory.createEmptyBorder(
                        30,
                        30,
                        30,
                        30
                )
        );

        titleLabel = new JLabel();

        titleLabel.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        coloringButton =
                ComponentsSwing.button(
                        "",
                        IconsSwing.loadFatCow(
                                IconsFatCow.PALETTE,
                                64
                        ),
                        () -> {
                            // Opens the coloring minigame.
                        }
                );

        coloringButton.setPreferredSize(
                new Dimension(
                        180,
                        180
                )
        );

        coloringButton.setHorizontalTextPosition(
                SwingConstants.CENTER
        );

        coloringButton.setVerticalTextPosition(
                SwingConstants.BOTTOM
        );

        coloringButton.setIconTextGap(12);

        JPanel gamesPanel =
                PanelsSwing.grid(
                        0,
                        3,
                        20,
                        20
                );

        JPanel coloringPanel =
                PanelsSwing.panel(
                        new GridBagLayout()
                );

        coloringPanel.add(
                coloringButton
        );

        gamesPanel.add(
                coloringPanel
        );

        add(
                titleLabel,
                BorderLayout.NORTH
        );

        add(
                gamesPanel,
                BorderLayout.CENTER
        );

        updateTexts();
    }

    // ----------------------------------------
    // I18N
    // ----------------------------------------

    /**
     * Updates all translated texts displayed by the panel.
     */
    public void updateTexts() {
        titleLabel.setText(
                Messages.text(
                        "home.title"
                )
        );

        coloringButton.setText(
                Messages.text(
                        "game.coloring.name"
                )
        );
    }
}