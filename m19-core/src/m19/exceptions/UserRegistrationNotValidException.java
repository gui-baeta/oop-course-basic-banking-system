package m19.exceptions;

/** Launched when user registration isn't valid. */
public class UserRegistrationNotValidException extends Exception {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 201901101348L;

    private String _userName;

    private String _userMail;

    public UserRegistrationNotValidException(String userName, String userMail) {
        _userName = userName;
        _userMail = userMail;
    }

    public String getUserName() {
        return _userName;
    }

    public String getUserMail() {
        return _userMail;
    }
}
