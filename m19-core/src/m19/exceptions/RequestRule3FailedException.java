package m19.exceptions;

/** Launched when request work rule 3 failed. */
public class RequestRule3FailedException extends Exception {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 201901101348L;

    private int _userId;

    private int _workId;

    public RequestRule3FailedException(int userId, int workId) {
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
