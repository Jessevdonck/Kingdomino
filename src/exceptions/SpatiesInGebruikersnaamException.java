package exceptions;

public class SpatiesInGebruikersnaamException extends RuntimeException
{
    public SpatiesInGebruikersnaamException() {
        super("Je gebruikersnaam mag niet uit enkel spaties bestaan");
    }

    public SpatiesInGebruikersnaamException(String message) {
        super(message);

    }
}
