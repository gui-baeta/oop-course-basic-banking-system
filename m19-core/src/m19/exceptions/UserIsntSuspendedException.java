package m19.exceptions;

/** Launched when a specified user isn't suspended. */
public class UserIsntSuspendedException extends Exception {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 201901101348L;

    private int _userId;

    public UserIsntSuspendedException(int userId) {
        _userId = userId;
    }

    public int getUserId() {
        return _userId;
    }
}
