package cat.andreu.jovia;

import cat.andreu.jovia.games.GameEngineRegistry;
import cat.andreu.jovia.games.GamePackageLoader;
import cat.andreu.jovia.games.GamePackageRegistry;
import cat.andreu.jovia.games.GameResourceLoader;
import cat.andreu.jovia.games.GameService;
import cat.andreu.jovia.games.coloring.ColoringGameEngine;
import cat.andreu.jovia.i18n.Messages;
import cat.andreu.jovia.navigation.Navigator;
import cat.andreu.jovia.navigation.Screen;
import cat.andreu.jovia.view.HomePanel;
import cat.andreu.jovia.view.LanguageSelectionPanel;
import cat.andreu.jovia.games.GamePackageException;

import io.github.agarriga18696.andreuutils.swing.*;

import javax.swing.JFrame;
import javax.swing.JMenuItem;

/**
 * Main Jovia application.
 *
 * @author Andreu
 * @version 2.2
 */
public final class JoviaApplication extends GuiApplicationBase {

    // ----------------------------------------
    // ATTRIBUTES
    // ----------------------------------------

    private JFrame mainFrame;
    private Navigator navigator;
    private GameService gameService;
    private JMenuItem openGameMenuItem;

    // ----------------------------------------
    // INITIALIZATION
    // ----------------------------------------

    @Override
    protected void initialize() {
        initializeGameService();

        mainFrame =
                FramesSwing.frame(
                        Messages.text("app.title"),
                        900,
                        650
                );

        navigator =
                new Navigator();

        HomePanel homePanel =
                new HomePanel(
                        gameService
                );

        LanguageSelectionPanel languageSelectionPanel =
                new LanguageSelectionPanel(
                        navigator,
                        homePanel,
                        () -> createMenuBar(
                                homePanel
                        )
                );

        navigator.addScreen(
                Screen.LANGUAGE,
                languageSelectionPanel
        );

        navigator.addScreen(
                Screen.HOME,
                homePanel
        );

        mainFrame.setContentPane(
                navigator.getContainer()
        );

        navigator.show(
                Screen.LANGUAGE
        );

        mainFrame.setVisible(
                true
        );
    }

    private void initializeGameService() {
        GameEngineRegistry engineRegistry =
                new GameEngineRegistry();

        engineRegistry.register(
                new ColoringGameEngine()
        );

        GamePackageRegistry packageRegistry =
                new GamePackageRegistry();

        GamePackageLoader packageLoader =
                new GamePackageLoader();

        GameResourceLoader resourceLoader =
                new GameResourceLoader();

        gameService =
                new GameService(
                        packageLoader,
                        packageRegistry,
                        engineRegistry,
                        resourceLoader
                );
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
                        .configureFileMenu(
                                fileMenu -> {
                                    openGameMenuItem =
                                            MenusSwing.item(
                                                    Messages.text(
                                                            "menu.file.openGame"
                                                    ),
                                                    IconsSwing.load(
                                                            IconsFugue.FOLDER_HORIZONTAL_OPEN,
                                                            16
                                                    ),
                                                    () -> openGame(homePanel)
                                            );

                                    fileMenu.insert(
                                            openGameMenuItem,
                                            0
                                    );
                                }
                        )
                        .onHome(
                                () -> navigator.show(
                                        Screen.HOME
                                )
                        )
                        .onExit(
                                mainFrame::dispose
                        )
                        .onAbout(
                                this::showAbout
                        )
                        .onLanguageChanged(
                                _ -> {
                                    homePanel.updateTexts();
                                    updateMenuTexts();
                                }
                        )
                        .build()
        );

        mainFrame.revalidate();
        mainFrame.repaint();
    }

    /**
     * Updates Jovia-specific menu texts.
     */
    private void updateMenuTexts() {
        if (openGameMenuItem != null) {
            openGameMenuItem.setText(
                    Messages.text(
                            "menu.file.openGame"
                    )
            );
        }
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

    // ----------------------------------------
    // GAME OPENING
    // ----------------------------------------

    /**
     * Opens a Jovia game package selected by the user.
     *
     * @param homePanel Home panel to refresh after loading the game.
     */
    private void openGame(
            HomePanel homePanel
    ) {
        DialogsSwing.chooseOpenFile(
                mainFrame,
                Messages.text(
                        "file.jovia.description"
                ),
                "jovia"
        ).ifPresent(file -> {
            try {
                gameService.openGame(
                        file.toPath()
                );

                homePanel.refreshGames();

                navigator.show(
                        Screen.HOME
                );

            } catch (GamePackageException exception) {
                DialogsSwing.error(
                        mainFrame,
                        Messages.text(
                                "game.open.error.title"
                        ),
                        Messages.text(
                                "game.open.error.message",
                                exception.getMessage()
                        )
                );
            }
        });
    }
}