package exceptions;

import GUI.TaalController;

import java.util.Locale;
import java.util.ResourceBundle;

public class OntbrekendGeboortejaarException extends RuntimeException
{
    public OntbrekendGeboortejaarException() {
        super(getErrorMessage());
    }

    public OntbrekendGeboortejaarException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

    private static String getErrorMessage() {
        String gekozenTaal = TaalController.getInstance().getLanguage();

        Locale locale = new Locale(gekozenTaal);
        ResourceBundle bundle = ResourceBundle.getBundle("resourcebundles.lang", locale);

        return bundle.getString("OntbrekendGeboortejaarException");
    }

}
