package m19.app.main;

import m19.LibraryManager;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.Input;

/**
 * 4.1.3. Advance the current date.
 */
public class DoAdvanceDate extends Command<LibraryManager> {

    Input<Integer> _days;

    /**
    * @param receiver
    */
    public DoAdvanceDate(LibraryManager receiver) {
        super(Label.ADVANCE_DATE, receiver);
        _days = _form.addIntegerInput(Message.requestDaysToAdvance());
    }

    /** @see pt.tecnico.po.ui.Command#execute() */
    @Override
    public final void execute() {
        _form.parse();

        _receiver.incrementDay(_days.value());
    }
}
