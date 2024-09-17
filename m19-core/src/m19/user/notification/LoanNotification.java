package m19.user.notification;

import m19.work.Work;

public class LoanNotification extends Notification {

	private static final long serialVersionUID = 201901101348L;

	/**
     * Constructor
     * @param work
     */
	public LoanNotification(Work work) {
		super(work);
	}

	/**
	* Create string representation of LoanNotification for printing
	* @return
	*/
	@Override
	public String toString() {
		return "REQUISIÇÃO: " + super.toString();
	}
}
