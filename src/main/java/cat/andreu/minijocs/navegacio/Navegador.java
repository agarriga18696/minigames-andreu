package cat.andreu.minijocs.navegacio;

import javax.swing.*;
import java.awt.*;

public final class Navegador {

    private final CardLayout cardLayout;
    private final JPanel pnlControlador;

    public Navegador() {
        cardLayout = new CardLayout();
        pnlControlador = new JPanel(cardLayout);
    }

    public JPanel obtenirContenidor() {
        return pnlControlador;
    }

    public void afegirPantalla(String nom, JPanel panell) {
        pnlControlador.add(panell, nom);
    }

    public void mostrar(String nom) {
        cardLayout.show(pnlControlador, nom);
    }

}
