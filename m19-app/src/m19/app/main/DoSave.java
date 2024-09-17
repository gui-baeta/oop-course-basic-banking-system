package m19.app.main;

import java.io.IOException;

import m19.LibraryManager;
import m19.exceptions.MissingFileAssociationException;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.Input;

/**
 * 4.1.1. Save to file under current name (if unnamed, query for name).
 */
public class DoSave extends Command<LibraryManager> {

    Input<String> _filename;

    /**
    * @param receiver
    */
    public DoSave(LibraryManager receiver) {
        super(Label.SAVE, receiver);
        _filename = _form.addStringInput(Message.newSaveAs());
    }

    /** @see pt.tecnico.po.ui.Command#execute() */
    @Override
    public final void execute() {
        try {
            _receiver.save();
        }
        catch (MissingFileAssociationException mfae) {
            try {
                _form.parse();
                _receiver.saveAs(_filename.value());
            }
            catch (MissingFileAssociationException | IOException e2) {
                mfae.printStackTrace();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
