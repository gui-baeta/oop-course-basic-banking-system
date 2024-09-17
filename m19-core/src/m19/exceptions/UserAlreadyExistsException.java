package m19.exceptions;

/** Launched when an user already exists. */
public class UserAlreadyExistsException extends Exception {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 201901101348L;

    private int _userId;

    public UserAlreadyExistsException(int userId) {
        _userId = userId;
    }

    public int getUserId() {
        return _userId;
    }
}
