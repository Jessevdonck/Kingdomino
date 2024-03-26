package exceptions;

public class TeJongeGebruikerException extends RuntimeException
{
    public TeJongeGebruikerException() {
        super("Je moet minstens 6 jaar oud zijn.");
    }

    public TeJongeGebruikerException(String message) {
        super(message);

    }
}
