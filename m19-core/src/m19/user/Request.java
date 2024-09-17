package m19.user;

import java.io.Serializable;

import m19.work.Work;

public class Request implements Serializable {

    private static final long serialVersionUID = 201901101348L;

    private int _requestId;

    private boolean _returned = false;

    private Work _requested;

    private int _dueDate;

    /**
    * Constructor
    * @param requestId
    * @param work    
    * @param user
    * @param currentTime
    */
    public Request(int requestId, Work work, User user, int currentTime) {
        _requestId = requestId;
        _returned = false;
        _requested = work;
        _dueDate = currentTime + user.getStatus().availableDays(work.getNCopies());
    }

    /**
    * Returns value of _requestId
    * @return
    */
    public int getRequestId() {
        return _requestId;
    }

    /**
    * Sets new value of _requested
    * @param requested
    */
    public void setWorkRequested(Work requested) {
        _requested = requested;
    }

    /**
    * Returns value of _requested
    * @return
    */
    public Work getWorkRequested() {
        return _requested;
    }

    /**
    * Sets new value of _dueDate
    * @param dueDate
    */
    public void setDueDate(int dueDate) {
        _dueDate = dueDate;
    }

    /**
    * Returns value of _dueDate
    * @return
    */
    public int getDueDate() {
        return _dueDate;
    }

    /**
    * Sets new value of _returnstate
    * @param returnstate
    */
    public void setReturnedState(boolean returnstate) {
        _returned = returnstate;
    }

    /**
    * Returns value of _returned
    * @return
    */
    public boolean hasBeenReturned() {
        return _returned;
    }

    /**
    * Returns fine of a work 
    * @param currentTime
    * @return fine
    */
    public int calculateFine(int currentTime) {
        return (currentTime - getDueDate()) * 5;
    }

    /**
    * Checks if currentTime is past dueDate
    * @param currentTime
    * @return boolean
    */
    public boolean dueDateCheck(int currentTime) {
        return getDueDate() >= currentTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        Request rq = (Request) o;
        return this.getWorkRequested().equals(rq.getWorkRequested());
    }

    @Override
    public int hashCode() {
        return getRequestId();
    }
}
