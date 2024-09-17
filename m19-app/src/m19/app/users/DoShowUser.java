package m19.app.users;

import m19.LibraryManager;
import m19.exceptions.UserDoesntExistException;
import m19.app.exceptions.NoSuchUserException;
import m19.user.User;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
/**
 * 4.2.2. Show specific user.
 */
public class DoShowUser extends Command<LibraryManager> {

    Input<Integer> _userId;

    /**
    * @param receiver
    */
    public DoShowUser(LibraryManager receiver) {
        super(Label.SHOW_USER, receiver);
        _userId = _form.addIntegerInput(Message.requestUserId());
    }

    /** @see pt.tecnico.po.ui.Command#execute() */
    @Override
    public final void execute() throws DialogException, NoSuchUserException{
        _form.parse();

        try{
            _display.popup(_receiver.getUser(_userId.value()));
        } catch(UserDoesntExistException udee) {
            throw new NoSuchUserException(_userId.value());
        }
    }

}
