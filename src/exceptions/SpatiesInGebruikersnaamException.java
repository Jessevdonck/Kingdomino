package exceptions;

import GUI.TaalController;

import java.util.Locale;
import java.util.ResourceBundle;

public class SpatiesInGebruikersnaamException extends RuntimeException
{
    public SpatiesInGebruikersnaamException() {
        super(getErrorMessage());
    }

    public SpatiesInGebruikersnaamException(String message) {
        super(message);

    }

    private static String getErrorMessage() {
        String gekozenTaal = TaalController.getInstance().getLanguage();

        Locale locale = new Locale(gekozenTaal);
        ResourceBundle bundle = ResourceBundle.getBundle("resourcebundles.lang", locale);

        return bundle.getString("SpatiesInGebruikersnaamException");
    }
}
