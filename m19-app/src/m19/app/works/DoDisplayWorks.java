package m19.app.works;

import m19.LibraryManager;
import m19.work.Work;

import pt.tecnico.po.ui.Command;

/**
 * 4.3.2. Display all works.
 */
public class DoDisplayWorks extends Command<LibraryManager> {

    /**
    * @param receiver
    */
    public DoDisplayWorks(LibraryManager receiver) {
        super(Label.SHOW_WORKS, receiver);
    }

    /** @see pt.tecnico.po.ui.Command#execute() */
    @Override
    public final void execute() {
        for (Work work : _receiver.getWorks()) {
            _display.addLine(work.toString());
        }
        _display.display();
    }
}
