package m19.work;

public class DVD extends Work {

    private static final long serialVersionUID = 201901101348L;

    private String _director;

    private int _IGAC;

    /**
	* Returns value of _director
	* @return
	*/
	public String getDirector() {
		return _director;
	}

	/**
	* Sets new value of _director
	* @param
	*/
	public void setDirector(String director) {
		_director = director;
	}

	public String getWorkType() {
		return "DVD";
	}

	/**
	* Returns value of _IGAC
	* @return
	*/
	public int getIGAC() {
		return _IGAC;
	}

	/**
	* Sets new value of _IGAC
	* @param
	*/
	public void setIGAC(int IGAC) {
		_IGAC = IGAC;
	}

    public DVD(int workId, String title, String nCopies, String value, String category, String director, String IGAC) {
        super.setCommon(workId, title, nCopies, value, category);

        _director = director;
        _IGAC = Integer.parseInt(IGAC);
    }

    public boolean compareToWork(String needle) {
        final int length = needle.length();
        if (length == 0) {
            return true; // Empty string
        }

        boolean retval;

        final char firstLcase = Character.toLowerCase(needle.charAt(0));
        final char firstUcase = Character.toUpperCase(needle.charAt(0));

        String haystack = this.getDirector();
        retval = super.searchNeedleInHaystack(needle, length, haystack,
                                     firstLcase, firstUcase);

        haystack = this.getTitle();
        retval = retval || super.searchNeedleInHaystack(needle, length, haystack,
                                     firstLcase, firstUcase);

        return retval;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        DVD dvd = (DVD) o;
        return this.getWorkId() == dvd.getWorkId() &&
               this.getTitle().equals(dvd.getTitle()) &&
               this.getDirector().equals(dvd.getDirector());
    }

	/**
	* Create string representation of DVDs for printing
	* @return
	*/
	@Override
	public String toString() {
		return super.toString() + " - " + getDirector() + " - " + getIGAC();
	}
}
