package m19.work;

import java.io.Serializable;
import java.util.HashMap;

import m19.user.User;
import m19.work.category.Category;
import m19.work.category.Fiction;
import m19.work.category.Reference;
import m19.work.category.Scientific;

public abstract class Work implements Serializable {

    private static final long serialVersionUID = 201901101348L;

    private HashMap<Integer, User> _usersInterested;

    private int _workId;

    private String _title;

    private int _nCopies;

    private int _nCopiesLeft;

    private int _value;

	private Category _category;

	/**
    * user added to _usersInterested
    * @param user
    */
    public void addUserInterested(User user) {
        if (!_usersInterested.containsKey(user.getUserId())) {
            _usersInterested.put(user.getUserId(), user);
        }
    }

	/**
    * returns _usersInterested
    * @return
    */
    public HashMap<Integer, User> getUsersInterested() {
        return _usersInterested;
    }

	/**
	* finds user in notifications hash
	* @param user
    * @return user or null
    */
    public User getUserInterested(User user) {
        return getUsersInterested().get(user.getUserId());
    }

    /**
	* Returns value of _workId
	* @return
	*/
	public int getWorkId() {
		return _workId;
	}

	/**
	* Sets new value of _workId
	* @param
	*/
	public void setWorkId(int workId) {
		_workId = workId;
	}

	/**
	* Returns value of _title
	* @return
	*/
	public String getTitle() {
		return _title;
	}

	/**
	* Sets new value of _title
	* @param
	*/
	public void setTitle(String title) {
		_title = title;
	}

	/**
	* Returns value of _nCopies
	* @return
	*/
	public int getNCopies() {
		return _nCopies;
	}

	/**
	* Sets new value of _nCopies
	* @param
	*/
	public void setNCopies(int nCopies) {
		_nCopies = nCopies;
	}

	/**
	* Returns value of _nCopiesLeft
	* @return
	*/
	public int getNCopiesLeft() {
		return _nCopiesLeft;
	}

    /**
	* Increments value of _nCopiesLeft
	* @param
	*/
    public void incrementNCopiesLeft() {
        _nCopiesLeft++;
    }

    /**
	* Decrements value of _nCopiesLeft
	* @param
	*/
    public void decrementNCopiesLeft() {
        _nCopiesLeft--;
    }

	/**
	* Sets new value of _nCopiesLeft
	* @param
	*/
	public void setNCopiesLeft(int nCopiesLeft) {
		_nCopiesLeft = nCopiesLeft;
	}

	/**
	* Returns value of _value
	* @return
	*/
	public int getValue() {
		return _value;
	}

	/**
	* Sets new value of _value
	* @param
	*/
	public void setValue(int value) {
		_value = value;
	}

	public abstract String getWorkType();

	/**
	* Returns value of _category
	* @return
	*/
	public Category getCategory() {
		return _category;
	}

	/**
	* Sets new value of _category
	* @param
	*/
	public void setCategory(Category category) {
		_category = category;
	}

	/**
	* sets common attributes
	* @param workId
	* @param title
	* @param nCopies
	* @param value
	* @param category
    */
    public void setCommon(int workId, String title, String nCopies,
    String value, String category) {

        _usersInterested = new HashMap<Integer, User>();
        _workId = workId;
        _title = title;
        _nCopies = Integer.parseInt(nCopies);
        _nCopiesLeft = _nCopies;
        _value = Integer.parseInt(value);

        switch(category) {
            case "FICTION":
                _category = new Fiction();
                break;
            case "SCITECH":
                _category = new Scientific();
                break;
            case "REFERENCE":
                _category = new Reference();
                break;
        }
	}

    public boolean searchNeedleInHaystack(String needle, int length,
    String haystack, char firstLcase, char firstUcase) {
        // Go throught every section with len (needle's length) and compare...
        // ...it to the specified needle
        for (int i = haystack.length() - length; i >= 0; i--) {
            final char c = haystack.charAt(i);
            // Compare initial needle letter to current initial haystack...
            // ...letter before using heavier regionMatches method
            if (c != firstLcase && c != firstUcase) {
                continue;
            }
            // Check if the needle is in the...
            // ...current region, from (i) to (length)
            if (haystack.regionMatches(true, i, needle, 0, length)) {
                return true;
            }
        }
        return false;
    }

    public abstract boolean compareToWork(String token);

    @Override
    public abstract boolean equals(Object o);

    @Override
    public int hashCode() {
        return getWorkId();
    }

	@Override
	public String toString() {
		return getWorkId() + " - " + getNCopiesLeft() + " de  " +
        getNCopies() + " - " + getWorkType() + " - " + getTitle() +
        " - " + getValue() + " - " + getCategory();
	}
}
