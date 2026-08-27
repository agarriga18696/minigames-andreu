package cat.andreu.minijocs;

import aplicaciogui.AplicacioGuiBase;
import aplicaciogui.FinestresSwing;
import cat.andreu.minijocs.idioma.GestorIdioma;
import cat.andreu.minijocs.idioma.Idioma;
import cat.andreu.minijocs.navegacio.Navegador;
import cat.andreu.minijocs.vista.BarraMenu;
import cat.andreu.minijocs.vista.PanellInici;
import cat.andreu.minijocs.vista.PanellSeleccioIdioma;

import javax.swing.*;

/**
 * Representa la finestra principal de l'aplicació.
 */

public final class AplicacioMinijocs extends AplicacioGuiBase {

    //
    // ATRIBUTS
    //

    private JFrame frmPrincipal;
    private Navegador navegador;

    //
    // MÈTODES
    //

    @Override
    protected void inicialitzar() {
        frmPrincipal = FinestresSwing.frame(
                "Minijocs Andreu",
                900,
                650
        );

        navegador = new Navegador();
        PanellInici pnlInici = new PanellInici();

        PanellSeleccioIdioma pnlSeleccioIdioma =
                new PanellSeleccioIdioma(
                        navegador,
                        pnlInici,
                        () -> crearBarraMenu(pnlInici)
                );

        navegador.afegirPantalla(
                "IDIOMA",
                pnlSeleccioIdioma
        );

        navegador.afegirPantalla(
                "INICI",
                pnlInici
        );

        frmPrincipal.setContentPane(
                navegador.obtenirContenidor()
        );

        navegador.mostrar("IDIOMA");

        frmPrincipal.setLocationRelativeTo(null);
        frmPrincipal.setVisible(true);
    }

    private void crearBarraMenu(PanellInici panellInici) {
        BarraMenu barraMenu = new BarraMenu(
                frmPrincipal,
                navegador,
                () -> frmPrincipal.dispose(),
                idioma -> canviarIdioma(idioma, panellInici)
        );

        frmPrincipal.setJMenuBar(
                barraMenu.obtenirBarraMenu()
        );

        frmPrincipal.revalidate();
        frmPrincipal.repaint();
    }

    private void canviarIdioma(
            Idioma idioma,
            PanellInici panellInici
    ) {
        GestorIdioma.establirIdioma(idioma);

        panellInici.actualitzarTextos();

        crearBarraMenu(panellInici);
    }

}
