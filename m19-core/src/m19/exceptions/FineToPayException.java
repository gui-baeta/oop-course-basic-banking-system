package m19.exceptions;

import m19.user.Request;

/** Launched when user needs to pay a fine when returning a specified work. */
public class FineToPayException extends Exception {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 201901101348L;

    private int _userId;

    private int _workId;

    private int _fine;

    private Request _request;

    public FineToPayException(int userId, int workId, int fine,
    Request request) {
        _userId = userId;
        _workId = workId;
        _fine = fine;
        _request = request;
    }

    public int getUserId() {
        return _userId;
    }

    public int getWorkId() {
        return _workId;
    }

    public int getFine() {
        return _fine;
    }

    public Request getRequest() {
        return _request;
    }
}
