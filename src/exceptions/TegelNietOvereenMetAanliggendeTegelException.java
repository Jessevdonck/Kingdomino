package exceptions;

import GUI.TaalController;

import java.util.Locale;
import java.util.ResourceBundle;

public class TegelNietOvereenMetAanliggendeTegelException extends RuntimeException
{
    public TegelNietOvereenMetAanliggendeTegelException() {
        super(getErrorMessage());
    }

    public TegelNietOvereenMetAanliggendeTegelException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

    private static String getErrorMessage() {
        String gekozenTaal = TaalController.getInstance().getLanguage();

        Locale locale = new Locale(gekozenTaal);
        ResourceBundle bundle = ResourceBundle.getBundle("resourcebundles.lang", locale);

        return bundle.getString("TegelNietOvereenMetAanliggendeTegelException");
    }


}
