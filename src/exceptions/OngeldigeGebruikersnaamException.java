package exceptions;

public class OngeldigeGebruikersnaamException extends RuntimeException
{
    public OngeldigeGebruikersnaamException() {
        super("De gebruikersnaam moet uit minstens 6 karakters bestaan.");
    }

    public OngeldigeGebruikersnaamException(String message) {
        super(message);

    }
}
