package cat.andreu.jovia.view;

import cat.andreu.jovia.i18n.Messages;
import cat.andreu.jovia.navigation.Navigator;

import io.github.agarriga18696.andreuutils.core.Language;
import io.github.agarriga18696.andreuutils.core.LanguageManager;
import io.github.agarriga18696.andreuutils.swing.ComponentsSwing;
import io.github.agarriga18696.andreuutils.swing.IconsFlags;
import io.github.agarriga18696.andreuutils.swing.IconsSwing;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Represents the initial language selection panel.
 *
 * @author Andreu
 * @version 2.4
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

    private static String getLanguageName(
            Language language
    ) {

        return switch (language) {
            case CATALAN -> "Català";
            case SPANISH -> "Español";
            case ENGLISH -> "English";
        };
    }

    private static Icon getLanguageIcon(
            Language language
    ) {

        return switch (language) {
            case CATALAN -> IconsSwing.loadFlag(
                    IconsFlags.CATALONIA,
                    32
            );

            case SPANISH -> IconsSwing.loadFlag(
                    IconsFlags.ES,
                    32
            );

            case ENGLISH -> IconsSwing.loadFlag(
                    IconsFlags.GB,
                    32
            );
        };
    }

    private void configurePanel() {
        setLayout(new GridBagLayout());

        JPanel languagePanel =
                new JPanel(
                        new GridBagLayout()
                );

        GridBagConstraints constraints =
                new GridBagConstraints();

        constraints.gridx = 0;
        constraints.anchor = GridBagConstraints.CENTER;

        JLabel titleLabel =
                new JLabel();

        constraints.gridy = 0;
        constraints.insets =
                new Insets(
                        0,
                        0,
                        25,
                        0
                );

        languagePanel.add(
                titleLabel,
                constraints
        );

        JList<Language> languageList =
                ComponentsSwing.list(
                        Language.values()
                );

        JButton confirmButton =
                ComponentsSwing.button(
                        "",
                        () -> selectCurrentLanguage(
                                languageList
                        )
                );

        configureLanguageList(
                languageList,
                titleLabel,
                confirmButton
        );

        JScrollPane scrollPane =
                new JScrollPane(
                        languageList
                );

        scrollPane.setPreferredSize(
                new Dimension(
                        360,
                        160
                )
        );

        constraints.gridy = 1;
        constraints.insets =
                new Insets(
                        0,
                        0,
                        15,
                        0
                );

        languagePanel.add(
                scrollPane,
                constraints
        );

        constraints.gridy = 2;
        constraints.insets =
                new Insets(
                        0,
                        0,
                        0,
                        0
                );

        languagePanel.add(
                confirmButton,
                constraints
        );

        add(languagePanel);

        SwingUtilities.invokeLater(
                languageList::requestFocusInWindow
        );
    }

    // ----------------------------------------
    // LANGUAGE
    // ----------------------------------------

    private void configureLanguageList(
            JList<Language> languageList,
            JLabel titleLabel,
            JButton confirmButton
    ) {

        languageList.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        languageList.setVisibleRowCount(
                Language.values().length
        );

        languageList.setFixedCellHeight(
                48
        );

        languageList.setCellRenderer(
                new LanguageRenderer()
        );

        languageList.setSelectedValue(
                LanguageManager.getLanguage(),
                true
        );

        updateTexts(
                languageList.getSelectedValue(),
                titleLabel,
                confirmButton
        );

        languageList.addListSelectionListener(
                event -> {

                    if (!event.getValueIsAdjusting()) {
                        updateTexts(
                                languageList.getSelectedValue(),
                                titleLabel,
                                confirmButton
                        );
                    }
                }
        );

        languageList.addMouseListener(
                new MouseAdapter() {

                    @Override
                    public void mouseClicked(
                            MouseEvent event
                    ) {

                        if (event.getClickCount() == 2) {
                            selectCurrentLanguage(
                                    languageList
                            );
                        }
                    }
                }
        );

        languageList.getInputMap(
                JComponent.WHEN_FOCUSED
        ).put(
                KeyStroke.getKeyStroke(
                        KeyEvent.VK_ENTER,
                        0
                ),
                "selectLanguage"
        );

        languageList.getActionMap().put(
                "selectLanguage",
                new AbstractAction() {

                    @Override
                    public void actionPerformed(
                            ActionEvent event
                    ) {

                        selectCurrentLanguage(
                                languageList
                        );
                    }
                }
        );
    }

    private void updateTexts(
            Language language,
            JLabel titleLabel,
            JButton confirmButton
    ) {

        if (language == null) {
            confirmButton.setEnabled(false);
            return;
        }

        confirmButton.setEnabled(true);

        titleLabel.setText(
                Messages.text(
                        language,
                        "language.selection.title"
                )
        );

        confirmButton.setText(
                Messages.text(
                        language,
                        "action.confirm"
                )
        );
    }

    private void selectCurrentLanguage(
            JList<Language> languageList
    ) {

        Language language =
                languageList.getSelectedValue();

        if (language != null) {
            selectLanguage(
                    language
            );
        }
    }

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

    // ----------------------------------------
    // RENDERER
    // ----------------------------------------

    private static final class LanguageRenderer
            extends DefaultListCellRenderer {

        @Override
        public Component getListCellRendererComponent(
                JList<?> list,
                Object value,
                int index,
                boolean isSelected,
                boolean cellHasFocus
        ) {

            JLabel label =
                    (JLabel) super.getListCellRendererComponent(
                            list,
                            value,
                            index,
                            isSelected,
                            cellHasFocus
                    );

            if (value instanceof Language language) {
                label.setText(
                        "%s (%s)".formatted(
                                getLanguageName(
                                        language
                                ),
                                language.locale()
                                        .getLanguage()
                        )
                );

                label.setIcon(
                        getLanguageIcon(
                                language
                        )
                );

                label.setIconTextGap(
                        12
                );

                label.setBorder(
                        BorderFactory.createEmptyBorder(
                                6,
                                10,
                                6,
                                10
                        )
                );
            }

            return label;
        }
    }
}