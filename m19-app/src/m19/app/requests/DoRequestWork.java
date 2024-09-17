package m19.app.requests;

import m19.LibraryManager;
import m19.app.exceptions.NoSuchUserException;
import m19.app.exceptions.NoSuchWorkException;
import m19.app.exceptions.RuleFailedException;
import m19.exceptions.RequestRule3FailedException;
import m19.exceptions.RequestRuleFailedException;
import m19.exceptions.UserDoesntExistException;
import m19.exceptions.WorkDoesntExistException;
import m19.user.Request;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;

/**
 * 4.4.1. Request work.
 */
public class DoRequestWork extends Command<LibraryManager> {

	Input<Integer> _userId;
	Input<Integer> _workId;
	Input<Boolean> _wantToBeNotified;

	/**
	 * @param receiver
	 */
	public DoRequestWork(LibraryManager receiver) {
		super(Label.REQUEST_WORK, receiver);
		_userId = _form.addIntegerInput(Message.requestUserId());
		_workId = _form.addIntegerInput(Message.requestWorkId());
	}

	/** @see pt.tecnico.po.ui.Command#execute() */
	@Override
	public final void execute() throws DialogException {
		_form.parse();

		try{
			int dueDate = _receiver.requestWork(_userId.value(), _workId.value());
			_display.popup(Message.workReturnDay(_workId.value(), dueDate));
		} catch (RequestRule3FailedException rr3fe) {
			_form.clear();
			_wantToBeNotified = _form.addBooleanInput(
				Message.requestReturnNotificationPreference());
			_form.parse();

			_receiver.addInterest(rr3fe.getUserId(), rr3fe.getWorkId(),
											_wantToBeNotified.value());

			_form.clear();
			_userId = _form.addIntegerInput(Message.requestUserId());
			_workId = _form.addIntegerInput(Message.requestWorkId());
		} catch (RequestRuleFailedException rr3fe) {
			throw new RuleFailedException(rr3fe.getUserId(),
							rr3fe.getWorkId(), rr3fe.getRuleId());
		} catch(UserDoesntExistException udee) {
            throw new NoSuchUserException(udee.getUserId());
		} catch(WorkDoesntExistException wdee) {
			throw new NoSuchWorkException(wdee.getWorkId());
		}
	}
}
