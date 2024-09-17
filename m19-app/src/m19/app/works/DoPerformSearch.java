package m19.app.works;

import m19.LibraryManager;
import m19.work.Work;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.Input;

/**
 * 4.3.3. Perform search according to miscellaneous criteria.
 */
public class DoPerformSearch extends Command<LibraryManager> {

    Input<String> _token;

    /**
    * @param m
    */
    public DoPerformSearch(LibraryManager m) {
        super(Label.PERFORM_SEARCH, m);
        _token = _form.addStringInput(Message.requestSearchTerm());
    }

    /** @see pt.tecnico.po.ui.Command#execute() */
    @Override
    public final void execute() {
        _form.parse();

        for (Work w : _receiver.searchWork(_token.value())) {
            _display.addLine(w.toString());
        }
        _display.display();
    }
}
