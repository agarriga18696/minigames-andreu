package cat.andreu.jovia.view;

import cat.andreu.jovia.games.GamePackage;
import cat.andreu.jovia.games.GamePackageException;
import cat.andreu.jovia.games.GameService;
import cat.andreu.jovia.i18n.Messages;

import io.github.agarriga18696.andreuutils.core.LanguageManager;
import io.github.agarriga18696.andreuutils.swing.ComponentsSwing;
import io.github.agarriga18696.andreuutils.swing.IconsFatCow;
import io.github.agarriga18696.andreuutils.swing.IconsSwing;
import io.github.agarriga18696.andreuutils.swing.PanelsSwing;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Image;

/**
 * Represents the application home panel.
 *
 * @author Andreu
 * @version 2.2
 */
public final class HomePanel extends JPanel {

    // ----------------------------------------
    // CONSTANTS
    // ----------------------------------------

    private static final int GAME_ICON_SIZE = 64;

    private static final int MAX_GAME_ICON_SIZE = 2 * 1024 * 1024;

    // ----------------------------------------
    // ATTRIBUTES
    // ----------------------------------------

    private final GameService gameService;
    private final JLabel titleLabel;
    private final JPanel gamesPanel;

    // ----------------------------------------
    // CONSTRUCTOR
    // ----------------------------------------

    /**
     * Creates the home panel.
     *
     * @param gameService Game service used to obtain loaded games.
     */
    public HomePanel(
            GameService gameService
    ) {
        if (gameService == null) {
            throw new IllegalArgumentException(
                    "Game service cannot be null."
            );
        }

        this.gameService =
                gameService;

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

        titleLabel =
                new JLabel();

        titleLabel.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        gamesPanel =
                PanelsSwing.grid(
                        0,
                        3,
                        20,
                        20
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
    // GAMES
    // ----------------------------------------

    /**
     * Rebuilds the game grid using the currently loaded game packages.
     */
    public void refreshGames() {
        gamesPanel.removeAll();

        for (GamePackage gamePackage
                : gameService.getGames()) {

            gamesPanel.add(
                    createGamePanel(
                            gamePackage
                    )
            );
        }

        gamesPanel.revalidate();
        gamesPanel.repaint();
    }

    /**
     * Creates the visual panel for a loaded game package.
     *
     * @param gamePackage Game package.
     * @return Game panel.
     */
    private JPanel createGamePanel(
            GamePackage gamePackage
    ) {
        JButton gameButton =
                ComponentsSwing.button(
                        getGameName(
                                gamePackage
                        ),
                        loadGameIcon(
                                gamePackage
                        ),
                        () -> {
                            // Game execution will be connected next.
                        }
                );

        gameButton.setPreferredSize(
                new Dimension(
                        180,
                        180
                )
        );

        gameButton.setHorizontalTextPosition(
                SwingConstants.CENTER
        );

        gameButton.setVerticalTextPosition(
                SwingConstants.BOTTOM
        );

        gameButton.setIconTextGap(
                12
        );

        JPanel gamePanel =
                PanelsSwing.panel(
                        new GridBagLayout()
                );

        gamePanel.add(
                gameButton
        );

        return gamePanel;
    }

    /**
     * Loads and scales the icon of a game package.
     *
     * @param gamePackage Game package.
     * @return Game icon.
     */
    private Icon loadGameIcon(
            GamePackage gamePackage
    ) {
        try {
            byte[] data =
                    gameService.loadResource(
                            gamePackage,
                            gamePackage.iconPath(),
                            MAX_GAME_ICON_SIZE
                    );

            ImageIcon sourceIcon =
                    new ImageIcon(
                            data
                    );

            if (sourceIcon.getIconWidth() <= 0
                    || sourceIcon.getIconHeight() <= 0) {

                return getFallbackIcon();
            }

            Image scaledImage =
                    sourceIcon
                            .getImage()
                            .getScaledInstance(
                                    GAME_ICON_SIZE,
                                    GAME_ICON_SIZE,
                                    Image.SCALE_SMOOTH
                            );

            return new ImageIcon(
                    scaledImage
            );

        } catch (GamePackageException exception) {
            return getFallbackIcon();
        }
    }

    /**
     * Returns the fallback icon used when a package icon
     * cannot be loaded.
     *
     * @return Fallback game icon.
     */
    private Icon getFallbackIcon() {
        return IconsSwing.loadFatCow(
                IconsFatCow.PALETTE,
                GAME_ICON_SIZE
        );
    }

    /**
     * Returns the localized display name of a game package.
     *
     * @param gamePackage Game package.
     * @return Localized game name.
     */
    private String getGameName(
            GamePackage gamePackage
    ) {
        String languageCode =
                LanguageManager
                        .getLanguage()
                        .locale()
                        .getLanguage();

        return gamePackage.getName(
                languageCode
        );
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

        refreshGames();
    }
}