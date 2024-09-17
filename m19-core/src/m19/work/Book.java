package m19.work;

public class Book extends Work {

    private static final long serialVersionUID = 201901101348L;

    private String _author;

    private int _ISBN;

    /**
	* Returns value of _author
	* @return
	*/
	public String getAuthor() {
		return _author;
	}

	/**
	* Sets new value of _author
	* @param
	*/
	public void setAuthor(String author) {
		_author = author;
	}

	public String getWorkType() {
		return "Livro";
	}

	/**
	* Returns value of _ISBN
	* @return
	*/
	public int getISBN() {
		return _ISBN;
	}

	/**
	* Sets new value of _ISBN
	* @param
	*/
	public void setISBN(int ISBN) {
		_ISBN = ISBN;
	}

    public Book(int workId, String title, String nCopies, String value,
    String category, String author, String ISBN) {

        setCommon(workId, title, nCopies, value, category);

        _author =  author;
        _ISBN = Integer.parseInt(ISBN);
    }

    public boolean compareToWork(String needle) {
        final int length = needle.length();
        if (length == 0) {
            return true; // Empty string
        }

        boolean retval;

        final char firstLcase = Character.toLowerCase(needle.charAt(0));
        final char firstUcase = Character.toUpperCase(needle.charAt(0));

        String haystack = this.getAuthor();
        retval = super.searchNeedleInHaystack(needle, length, haystack,
                                    firstLcase, firstUcase);

        haystack = this.getTitle();
        retval = retval || super.searchNeedleInHaystack(needle, length,
                                    haystack, firstLcase, firstUcase);

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
        Book book = (Book) o;
        return this.getWorkId() == book.getWorkId() &&
               this.getTitle().equals(book.getTitle()) &&
               this.getAuthor().equals(book.getAuthor());
    }

	/**
	* Create string representation of Book for printing
	* @return
	*/
	@Override
	public String toString() {
		return super.toString() + " - " + getAuthor() + " - " + getISBN();
	}
}
