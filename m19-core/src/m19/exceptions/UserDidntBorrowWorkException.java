package m19.exceptions;

/** Launched when an user didn't borrow specified work. */
public class UserDidntBorrowWorkException extends Exception {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 201901101348L;

    private int _userId;

    private int _workId;

    public UserDidntBorrowWorkException(int userId, int workId) {
        _userId = userId;
        _workId = workId;
    }

    public int getUserId() {
        return _userId;
    }

    public int getWorkId() {
        return _workId;
    }
}
