package m19.exceptions;

/** Launched when a request work rule failed. */
public class RequestRuleFailedException extends Exception {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 201901101348L;

    private int _userId;

    private int _workId;

    private int _ruleId;

    public RequestRuleFailedException(int userId, int workId, int ruleId) {
        _userId = userId;
        _workId = workId;
        _ruleId = ruleId;
    }

    public int getUserId() {
        return _userId;
    }

    public int getWorkId() {
        return _workId;
    }

    public int getRuleId() {
        return _ruleId;
    }
}
