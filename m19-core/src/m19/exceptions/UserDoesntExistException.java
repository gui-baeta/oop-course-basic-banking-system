package m19.exceptions;

/** Launched when an user doesn't exist. */
public class UserDoesntExistException extends Exception {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 201901101348L;

    private int _userId;

    public UserDoesntExistException(int userId) {
        _userId = userId;
    }

    public int getUserId() {
        return _userId;
    }
}
