package m19.exceptions;

/** Launched when a specified work doesn't exist. */
public class WorkDoesntExistException extends Exception {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 201901101348L;

    private int _workId;

    public WorkDoesntExistException(int workId) {
        _workId = workId;
    }

    public int getWorkId() {
        return _workId;
    }
}
