package m19.user.notification;

import java.io.Serializable;

import m19.work.Work;

public abstract class Notification implements Serializable {

    private static final long serialVersionUID = 201901101348L;

    Work _work;

    /**
     * Constructor
     * @param work
     */
    public Notification(Work work) {
        setWork(work);
    }

    /**
     * Set value of _work
     * @param work
     */
    public void setWork(Work work) {
        _work = work;
    }

    /**
    * Returns value of _work
    * @return
    */
    public Work getWork() {
        return _work;
    }

	/**
	* Create string representation of Notification for printing
	* @return
	*/
	@Override
	public String toString() {
        return getWork().toString();
    }
}
