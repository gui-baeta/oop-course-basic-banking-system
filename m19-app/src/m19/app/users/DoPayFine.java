package m19.app.users;

import m19.LibraryManager;
import m19.app.exceptions.NoSuchUserException;
import m19.app.exceptions.UserIsActiveException;
import m19.exceptions.UserDoesntExistException;
import m19.exceptions.UserIsntSuspendedException;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;

/**
 * 4.2.5. Settle a fine.
 */
public class DoPayFine extends Command<LibraryManager> {

    Input<Integer> _userId;

    /**
    * @param receiver
    */
    public DoPayFine(LibraryManager receiver) {
        super(Label.PAY_FINE, receiver);
        _userId = _form.addIntegerInput(Message.requestUserId());
    }

    /** @see pt.tecnico.po.ui.Command#execute() */
    @Override
    public final void execute() throws DialogException {
        _form.parse();

        try {
            _receiver.payFine(_userId.value());
        } catch(UserDoesntExistException udee) {
            throw new NoSuchUserException(_userId.value());
        } catch (UserIsntSuspendedException uise) {
            throw new UserIsActiveException(uise.getUserId());
        }
    }
}
