package cat.andreu.jovia;

import cat.andreu.jovia.i18n.Messages;
import cat.andreu.jovia.navigation.Navigator;
import cat.andreu.jovia.view.HomePanel;
import cat.andreu.jovia.view.LanguageSelectionPanel;

import io.github.agarriga18696.andreuutils.swing.ApplicationMenuBarSwing;
import io.github.agarriga18696.andreuutils.swing.DialogsSwing;
import io.github.agarriga18696.andreuutils.swing.FramesSwing;
import io.github.agarriga18696.andreuutils.swing.GuiApplicationBase;

import javax.swing.JFrame;

/**
 * Main application for Jovia.
 *
 * @author Andreu
 * @version 2.1
 */
public final class JoviaApplication extends GuiApplicationBase {

    // ----------------------------------------
    // CONSTANTS
    // ----------------------------------------

    private static final String SCREEN_LANGUAGE = "LANGUAGE";
    private static final String SCREEN_HOME = "HOME";

    // ----------------------------------------
    // ATTRIBUTES
    // ----------------------------------------

    private JFrame mainFrame;
    private Navigator navigator;

    // ----------------------------------------
    // INITIALIZATION
    // ----------------------------------------

    /**
     * Initializes the main application window and its screens.
     */
    @Override
    protected void initialize() {
        mainFrame =
                FramesSwing.frame(
                        Messages.text("app.title"),
                        900,
                        650
                );

        navigator = new Navigator();

        HomePanel homePanel =
                new HomePanel();

        LanguageSelectionPanel languageSelectionPanel =
                new LanguageSelectionPanel(
                        navigator,
                        homePanel,
                        () -> createMenuBar(homePanel)
                );

        navigator.addScreen(
                SCREEN_LANGUAGE,
                languageSelectionPanel
        );

        navigator.addScreen(
                SCREEN_HOME,
                homePanel
        );

        mainFrame.setContentPane(
                navigator.getContainer()
        );

        navigator.show(
                SCREEN_LANGUAGE
        );

        mainFrame.setVisible(true);
    }

    // ----------------------------------------
    // MENU BAR
    // ----------------------------------------

    private void createMenuBar(
            HomePanel homePanel
    ) {

        mainFrame.setJMenuBar(
                ApplicationMenuBarSwing
                        .builder(mainFrame)
                        .onHome(
                                () -> navigator.show(
                                        SCREEN_HOME
                                )
                        )
                        .onExit(
                                mainFrame::dispose
                        )
                        .onAbout(
                                this::showAbout
                        )
                        .onLanguageChanged(
                                _ -> homePanel.updateTexts()
                        )
                        .build()
        );

        mainFrame.revalidate();
        mainFrame.repaint();
    }

    // ----------------------------------------
    // ABOUT
    // ----------------------------------------

    private void showAbout() {
        DialogsSwing.info(
                mainFrame,
                Messages.text("about.title"),
                Messages.text("about.message")
        );
    }
}