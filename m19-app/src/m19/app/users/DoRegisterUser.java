package m19.app.users;

import m19.LibraryManager;
import m19.app.exceptions.UserRegistrationFailedException;
import m19.exceptions.UserRegistrationNotValidException;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;

/**
 * 4.2.1. Register new user.
 */
public class DoRegisterUser extends Command<LibraryManager> {

    Input<String> _userName;
    Input<String> _userMail;

    /**
    * @param receiver
    */
    public DoRegisterUser(LibraryManager receiver) {
        super(Label.REGISTER_USER, receiver);
        _userName = _form.addStringInput(Message.requestUserName());
        _userMail = _form.addStringInput(Message.requestUserEMail());
    }

    /** @see pt.tecnico.po.ui.Command#execute() */
    @Override
    public final void execute() throws DialogException {
        _form.parse();

        try {
            int userId = _receiver.registerUser(_userName.value(),
                                                _userMail.value());
            _display.popup(Message.userRegistrationSuccessful(userId));
        } catch(UserRegistrationNotValidException urnve) {
            throw new UserRegistrationFailedException(urnve.getUserName(),
                                                        urnve.getUserMail());
        }
    }
}
