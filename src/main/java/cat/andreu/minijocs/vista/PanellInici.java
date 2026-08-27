package cat.andreu.minijocs.vista;

import aplicaciogui.ComponentsSwing;
import aplicaciogui.IconesFatCow;
import aplicaciogui.IconesSwing;
import aplicaciogui.PanellsSwing;
import cat.andreu.minijocs.idioma.GestorIdioma;

import javax.swing.*;
import java.awt.*;

/**
 * Representa el panell d'inici.
 */

public final class PanellInici extends JPanel {

    //
    // ATRIBUTS
    //

    private final JLabel lblTitol;
    private final JButton btnColorar;

    //
    // CONSTRUCTOR
    //

    public PanellInici() {
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        lblTitol = new JLabel();
        lblTitol.setHorizontalAlignment(SwingConstants.CENTER);

        btnColorar = ComponentsSwing.boto(
                "",
                IconesSwing.carregarFatCow(IconesFatCow.PALETTE, 64),
                () -> {
                    // Obrirà el minijoc.
                }
        );

        btnColorar.setPreferredSize(new Dimension(180, 180));
        btnColorar.setHorizontalTextPosition(SwingConstants.CENTER);
        btnColorar.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnColorar.setIconTextGap(12);

        JPanel pnlJocs = PanellsSwing.grid(0, 3, 20, 20);
        JPanel pnlColorar = PanellsSwing.panel(new GridBagLayout());

        pnlColorar.add(btnColorar);
        pnlJocs.add(pnlColorar);

        add(lblTitol, BorderLayout.NORTH);
        add(pnlJocs, BorderLayout.CENTER);
    }

    //
    // MÈTODES
    //

    public void actualitzarTextos() {
        lblTitol.setText(
                GestorIdioma.text("inici.titol")
        );

        btnColorar.setText(
                GestorIdioma.text("joc.colorar.nom")
        );
    }

}
