package m19.app.works;

import m19.LibraryManager;
import m19.app.exceptions.NoSuchWorkException;
import m19.exceptions.WorkDoesntExistException;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;

/**
 * 4.3.1. Display work.
 */
public class DoDisplayWork extends Command<LibraryManager> {

    Input<Integer> _workId;

    /**
    * @param receiver
    */
    public DoDisplayWork(LibraryManager receiver) {
        super(Label.SHOW_WORK, receiver);
        _workId = _form.addIntegerInput(Message.requestWorkId());
    }

    /** @see pt.tecnico.po.ui.Command#execute() */
    @Override
    public final void execute() throws DialogException {
        _form.parse();

        try {
            _display.popup(_receiver.getWork(_workId.value()));
        }
        catch (WorkDoesntExistException wdee) {
            throw new NoSuchWorkException(wdee.getWorkId());
        }
    }

}
