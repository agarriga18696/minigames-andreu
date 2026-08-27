package cat.andreu.minijocs.vista;

import aplicaciogui.ComponentsSwing;
import aplicaciogui.IconesBanderes;
import aplicaciogui.IconesSwing;
import cat.andreu.minijocs.idioma.GestorIdioma;
import cat.andreu.minijocs.idioma.Idioma;
import cat.andreu.minijocs.navegacio.Navegador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Representa el panell de sel·lecció d'idioma de l'inici.
 */

public final class PanellSeleccioIdioma extends JPanel {

    //
    // ATRIBUTS
    //

    private final Navegador navegador;
    private final PanellInici panellInici;
    private final Runnable accioIdiomaSeleccionat;

    //
    // CONSTRUCTOR
    //

    public PanellSeleccioIdioma(
            Navegador navegador,
            PanellInici panellInici,
            Runnable accioIdiomaSeleccionat
    ) {
        this.navegador = navegador;
        this.panellInici = panellInici;
        this.accioIdiomaSeleccionat = accioIdiomaSeleccionat;

        configurarPanell();
    }

    //
    // MÈTODES
    //

    private void configurarPanell() {
        setLayout(new GridBagLayout());

        JPanel pnlIdiomes = new JPanel();
        pnlIdiomes.setLayout(
                new BoxLayout(pnlIdiomes, BoxLayout.Y_AXIS)
        );

        JLabel lblTitol = new JLabel("Selecciona l'idioma / Choose your language");
        lblTitol.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnCatala = ComponentsSwing.boto(
                "Català",
                IconesSwing.carregarBandera(IconesBanderes.CATALONIA, 32),
                KeyEvent.VK_C,
                () -> seleccionarIdioma(Idioma.CATALA)
        );

        JButton btnCastella = ComponentsSwing.boto(
                "Castellano",
                IconesSwing.carregarBandera(IconesBanderes.ES, 32),
                KeyEvent.VK_S,
                () -> seleccionarIdioma(Idioma.CASTELLA)
        );

        JButton btnAngles = ComponentsSwing.boto(
                "English",
                IconesSwing.carregarBandera(IconesBanderes.GB, 32),
                KeyEvent.VK_E,
                () -> seleccionarIdioma(Idioma.ANGLES)
        );

        btnCatala.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCastella.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnAngles.setAlignmentX(Component.CENTER_ALIGNMENT);

        pnlIdiomes.add(lblTitol);
        pnlIdiomes.add(Box.createVerticalStrut(30));

        pnlIdiomes.add(btnCatala);
        pnlIdiomes.add(Box.createVerticalStrut(15));

        pnlIdiomes.add(btnCastella);
        pnlIdiomes.add(Box.createVerticalStrut(15));

        pnlIdiomes.add(btnAngles);

        add(pnlIdiomes);
    }

    private void seleccionarIdioma(Idioma idioma) {
        GestorIdioma.establirIdioma(idioma);

        panellInici.actualitzarTextos();

        accioIdiomaSeleccionat.run();

        navegador.mostrar("INICI");
    }

}
