package cat.andreu.minijocs.vista;

import aplicaciogui.*;
import cat.andreu.minijocs.idioma.GestorIdioma;
import cat.andreu.minijocs.idioma.Idioma;
import cat.andreu.minijocs.navegacio.Navegador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.function.Consumer;

/**
 * Gestiona la barra de menú principal de l'aplicació.
 */

public final class BarraMenu {

    //
    // ATRIBUTS
    //

    private final Component componentPrincipal;
    private final Navegador navegador;
    private final JMenuBar barraMenu;
    private final Runnable accioSortir;
    private final Consumer<Idioma> accioCanviarIdioma;

    //
    // CONSTRUCTOR
    //

    public BarraMenu(
            Component componentPrincipal,
            Navegador navegador,
            Runnable accioSortir,
            Consumer<Idioma> accioCanviarIdioma
    ) {
        this.componentPrincipal = componentPrincipal;
        this.navegador = navegador;
        this.accioSortir = accioSortir;
        this.accioCanviarIdioma = accioCanviarIdioma;

        barraMenu = MenusSwing.barraMenu();

        crearMenuJoc();
        crearMenuIdioma();
        crearMenuTemes();
        crearMenuAjuda();
    }

    //
    // MÈTODES
    //

    private void crearMenuJoc() {
        JMenu mnuJoc = MenusSwing.menu(
                GestorIdioma.text("menu.joc"),
                GestorIdioma.text("menu.joc.mnemonic").charAt(0)
        );

        JMenuItem itmInici = MenusSwing.item(
                GestorIdioma.text("menu.inici"),
                IconesSwing.carregar(IconesSwing.INICI),
                KeyStroke.getKeyStroke(
                        KeyEvent.VK_H,
                        InputEvent.CTRL_DOWN_MASK
                ),
                () -> navegador.mostrar("INICI")
        );

        JMenuItem itmSortir = MenusSwing.item(
                GestorIdioma.text("menu.sortir"),
                IconesSwing.carregar(IconesSwing.SORTIR),
                KeyStroke.getKeyStroke(
                        KeyEvent.VK_Q,
                        InputEvent.CTRL_DOWN_MASK
                ),
                accioSortir
        );

        mnuJoc.add(itmInici);
        mnuJoc.addSeparator();
        mnuJoc.add(itmSortir);

        barraMenu.add(mnuJoc);
    }

    private void crearMenuIdioma() {
        JMenu mnuIdioma = MenusSwing.menu(
                GestorIdioma.text("menu.idioma"),
                GestorIdioma.text("menu.idioma.mnemonic").charAt(0)
        );

        JRadioButtonMenuItem itmCatala = MenusSwing.radioItem(
                "Català",
                IconesSwing.carregarBandera(IconesBanderes.CATALONIA, 16),
                GestorIdioma.obtenirIdioma() == Idioma.CATALA,
                () -> accioCanviarIdioma.accept(Idioma.CATALA)
        );

        JRadioButtonMenuItem itmCastella = MenusSwing.radioItem(
                "Castellano",
                IconesSwing.carregarBandera(IconesBanderes.ES, 16),
                GestorIdioma.obtenirIdioma() == Idioma.CASTELLA,
                () -> accioCanviarIdioma.accept(Idioma.CASTELLA)
        );

        JRadioButtonMenuItem itmAngles = MenusSwing.radioItem(
                "English",
                IconesSwing.carregarBandera(IconesBanderes.GB, 16),
                GestorIdioma.obtenirIdioma() == Idioma.ANGLES,
                () -> accioCanviarIdioma.accept(Idioma.ANGLES)
        );

        ButtonGroup grupIdiomes = new ButtonGroup();

        grupIdiomes.add(itmCatala);
        grupIdiomes.add(itmCastella);
        grupIdiomes.add(itmAngles);

        mnuIdioma.add(itmCatala);
        mnuIdioma.add(itmCastella);
        mnuIdioma.add(itmAngles);

        barraMenu.add(mnuIdioma);
    }

    private void crearMenuTemes() {
        JMenu mnuTemes = MenuTemesSwing.crearMenuTemes(componentPrincipal);

        mnuTemes.setIcon(null);
        mnuTemes.setText(GestorIdioma.text("menu.temes"));
        mnuTemes.setMnemonic(GestorIdioma.text("menu.temes.mnemonic").charAt(0));

        barraMenu.add(mnuTemes);
    }

    private void crearMenuAjuda() {
        JMenu mnuAjuda = MenusSwing.menu(
                GestorIdioma.text("menu.ajuda"),
                GestorIdioma.text("menu.ajuda.mnemonic").charAt(0)
        );

        JMenuItem itmSobre = MenusSwing.item(
                GestorIdioma.text("menu.sobre"),
                IconesSwing.carregar(IconesSwing.INFORMACIO),
                KeyStroke.getKeyStroke(
                        KeyEvent.VK_F1,
                        0
                ),
                () -> DialegsSwing.info(
                        componentPrincipal,
                        GestorIdioma.text("sobre.titol"),
                        GestorIdioma.text("sobre.missatge")
                )
        );

        mnuAjuda.add(itmSobre);

        barraMenu.add(mnuAjuda);
    }

    public JMenuBar obtenirBarraMenu() {
        return barraMenu;
    }

}
