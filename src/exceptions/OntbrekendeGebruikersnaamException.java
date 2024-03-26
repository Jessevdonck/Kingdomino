package exceptions;

public class OntbrekendeGebruikersnaamException extends RuntimeException
{
    public OntbrekendeGebruikersnaamException() {
        super("Je moet een gebruikersnaam invullen.");
    }

    public OntbrekendeGebruikersnaamException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }
}
