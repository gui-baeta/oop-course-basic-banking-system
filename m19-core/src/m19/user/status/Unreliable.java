package m19.user.status;

public class Unreliable extends Status {

	private static final long serialVersionUID = 201901101348L;

	/**
    * Finds how many days after borrowing user can wait before missing dueDate
    * @param nCopies
    * @return days until return
    */
	public int availableDays(int nCopies) {
		return 2; // Any number of work copies
	}

	/**
	* Create string representation of Unreliable for printing
	* @return
	*/
	@Override
	public String toString() {
		return "FALTOSO";
	}
}
