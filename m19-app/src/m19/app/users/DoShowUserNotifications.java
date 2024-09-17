package m19.app.users;

import m19.LibraryManager;
import m19.app.exceptions.NoSuchUserException;
import m19.exceptions.UserDoesntExistException;

import m19.user.notification.Notification;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;

/**
 * 4.2.3. Show notifications of a specific user.
 */
public class DoShowUserNotifications extends Command<LibraryManager> {

    Input<Integer> _userId;

    /**
    * @param receiver
    */
    public DoShowUserNotifications(LibraryManager receiver) {
        super(Label.SHOW_USER_NOTIFICATIONS, receiver);
        _userId = _form.addIntegerInput(Message.requestUserId());
    }

    /** @see pt.tecnico.po.ui.Command#execute() */
    @Override
    public final void execute() throws DialogException, NoSuchUserException {
        _form.parse();

        try{
            for (Notification notification :
            _receiver.getUserNotifications(_userId.value())) {

                _display.addLine(notification.toString());
            }
            _display.display();
            _receiver.clearUserNotifications(_userId.value());
        } catch(UserDoesntExistException udee) {
            throw new NoSuchUserException(_userId.value());
        }
    }
}
