package cat.andreu.jovia.view;

import cat.andreu.jovia.i18n.Messages;
import cat.andreu.jovia.navigation.Navigator;

import io.github.agarriga18696.andreuutils.core.Language;
import io.github.agarriga18696.andreuutils.core.LanguageManager;
import io.github.agarriga18696.andreuutils.swing.ComponentsSwing;
import io.github.agarriga18696.andreuutils.swing.IconsFlags;
import io.github.agarriga18696.andreuutils.swing.IconsSwing;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.event.KeyEvent;

/**
 * Represents the initial language selection panel.
 *
 * @author Andreu
 * @version 2.2
 */
public final class LanguageSelectionPanel extends JPanel {

    // ----------------------------------------
    // ATTRIBUTES
    // ----------------------------------------

    private final Navigator navigator;
    private final HomePanel homePanel;
    private final Runnable languageSelectedAction;

    // ----------------------------------------
    // CONSTRUCTOR
    // ----------------------------------------

    /**
     * Creates the language selection panel.
     *
     * @param navigator              Application navigator.
     * @param homePanel              Home panel to refresh after selecting a language.
     * @param languageSelectedAction Action executed after selecting a language.
     */
    public LanguageSelectionPanel(
            Navigator navigator,
            HomePanel homePanel,
            Runnable languageSelectedAction
    ) {

        this.navigator = navigator;
        this.homePanel = homePanel;
        this.languageSelectedAction = languageSelectedAction;

        configurePanel();
    }

    // ----------------------------------------
    // CONFIGURATION
    // ----------------------------------------

    private void configurePanel() {
        setLayout(new GridBagLayout());

        JPanel languagePanel = new JPanel();

        languagePanel.setLayout(
                new BoxLayout(
                        languagePanel,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel titleLabel =
                new JLabel(
                        Messages.text(
                                "language.selection.title"
                        )
                );

        titleLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        JButton catalanButton =
                ComponentsSwing.button(
                        Messages.text(
                                "language.catalan"
                        ),
                        IconsSwing.loadFlag(
                                IconsFlags.CATALONIA,
                                32
                        ),
                        KeyEvent.VK_C,
                        () -> selectLanguage(Language.CATALAN)
                );

        JButton spanishButton =
                ComponentsSwing.button(
                        Messages.text(
                                "language.spanish"
                        ),
                        IconsSwing.loadFlag(
                                IconsFlags.ES,
                                32
                        ),
                        KeyEvent.VK_S,
                        () -> selectLanguage(Language.SPANISH)
                );

        JButton englishButton =
                ComponentsSwing.button(
                        Messages.text(
                                "language.english"
                        ),
                        IconsSwing.loadFlag(
                                IconsFlags.GB,
                                32
                        ),
                        KeyEvent.VK_E,
                        () -> selectLanguage(Language.ENGLISH)
                );

        catalanButton.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        spanishButton.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        englishButton.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        languagePanel.add(titleLabel);
        languagePanel.add(Box.createVerticalStrut(30));

        languagePanel.add(catalanButton);
        languagePanel.add(Box.createVerticalStrut(15));

        languagePanel.add(spanishButton);
        languagePanel.add(Box.createVerticalStrut(15));

        languagePanel.add(englishButton);

        add(languagePanel);
    }

    // ----------------------------------------
    // LANGUAGE
    // ----------------------------------------

    private void selectLanguage(
            Language language
    ) {

        LanguageManager.setLanguage(
                language
        );

        homePanel.updateTexts();

        languageSelectedAction.run();

        navigator.show("HOME");
    }
}