package m19.app.requests;

import m19.LibraryManager;
import m19.app.exceptions.NoSuchUserException;
import m19.app.exceptions.NoSuchWorkException;
import m19.app.exceptions.WorkNotBorrowedByUserException;
import m19.exceptions.FineToPayException;
import m19.exceptions.UserDidntBorrowWorkException;
import m19.exceptions.UserDoesntExistException;
import m19.exceptions.UserIsntSuspendedException;
import m19.exceptions.WorkDoesntExistException;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;

/**
 * 4.4.2. Return a work.
 */
public class DoReturnWork extends Command<LibraryManager> {

    Input<Integer> _userId;
    Input<Integer> _workId;
    Input<Boolean> _wantsToPayFine;

    /**
    * @param receiver
    */
    public DoReturnWork(LibraryManager receiver) {
        super(Label.RETURN_WORK, receiver);
        _userId = _form.addIntegerInput(Message.requestUserId());
        _workId = _form.addIntegerInput(Message.requestWorkId());
    }

    /** @see pt.tecnico.po.ui.Command#execute() */
    @Override
    public final void execute() throws DialogException {
        _form.parse();

        try {
            _receiver.returnWork(_userId.value(), _workId.value());
        } catch (FineToPayException ftpe) {
            _form.clear();

            // Show the user the fine he has to pay
            _display.popup(Message.showFine(ftpe.getUserId(), ftpe.getFine()));

            // Ask if the user wants to pay the fine
            _wantsToPayFine = _form.addBooleanInput(Message.requestFinePaymentChoice());
            _form.parse();

            // If user wants to pay the fine, let him
            _receiver.payFine(ftpe.getUserId(), _wantsToPayFine.value());

            _form.clear();
            _userId = _form.addIntegerInput(Message.requestUserId());
            _workId = _form.addIntegerInput(Message.requestWorkId());
        } catch (UserDoesntExistException udee) {
            throw new NoSuchUserException(udee.getUserId());
        } catch (WorkDoesntExistException wdee) {
            throw new NoSuchWorkException(wdee.getWorkId());
        } catch (UserDidntBorrowWorkException udbwe) {
            throw new WorkNotBorrowedByUserException(udbwe.getWorkId(),
                                                        udbwe.getUserId());
        }
    }
}
