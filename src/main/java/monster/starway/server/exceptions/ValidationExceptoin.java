package monster.starway.server.exceptions;

public class ValidationExceptoin extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    public ValidationExceptoin(String exception) {
        super(exception);
    }
}
