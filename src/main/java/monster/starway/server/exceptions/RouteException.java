package monster.starway.server.exceptions;

public class RouteException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    public RouteException(String exception) {
        super(exception);
    }
}
