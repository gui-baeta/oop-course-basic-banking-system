package m19.user.notification;

import m19.work.Work;

public class ReturnNotification extends Notification {

	private static final long serialVersionUID = 201901101348L;

	/**
     * Constructor
     * @param work
     */
	public ReturnNotification(Work work) {
		super(work);
	}

	/**
	* Create string representation of ReturnNotification for printing
	* @return
	*/
	@Override
	public String toString() {
		return "ENTREGA: " + super.toString();
	}
}
