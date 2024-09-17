package m19.user.status;

public class Normal extends Status {

	private static final long serialVersionUID = 201901101348L;

	/**
    * Finds how many days after borrowing user can wait before missing dueDate
    * @param nCopies
    * @return days until return
    */
	public int availableDays(int nCopies) {
		if (nCopies > 5) { // More than 5 work copies (>5)
			return 15;
		} else if (nCopies == 1) { // Only one work copy (=1)
			return 3;
		} else { // Equal to or less than 5 copies and more than 1 (<=5 && >1)
			return 8;
		}
	}

	/**
	* Create string representation of Normal for printing
	* @return
	*/
	@Override
	public String toString() {
		return "NORMAL";
	}
}
