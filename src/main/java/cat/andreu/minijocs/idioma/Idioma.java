package cat.andreu.minijocs.idioma;

import java.util.Locale;

/**
 * Defineix els idiomes disponibles de l'aplicació.
 */

public enum Idioma {

    CATALA("Català", "ca", Locale.of("ca")),
    CASTELLA("Castellano", "es", Locale.of("es")),
    ANGLES("English", "en", Locale.of("en"));

    private final String nom;
    private final String codi;
    private final Locale locale;

    Idioma(String nom, String codi, Locale locale) {
        this.nom = nom;
        this.codi = codi;
        this.locale = locale;
    }

    public String getNom() {
        return nom;
    }

    public String getCodi() {
        return codi;
    }

    public Locale getLocale() {
        return locale;
    }

}
