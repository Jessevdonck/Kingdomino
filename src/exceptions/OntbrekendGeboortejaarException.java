package exceptions;

public class OntbrekendGeboortejaarException extends RuntimeException
{
    public OntbrekendGeboortejaarException() {
        super("Je moet een geboortejaar invullen.");
    }

    public OntbrekendGeboortejaarException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }
}
