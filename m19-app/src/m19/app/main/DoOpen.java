package m19.app.main;

import m19.LibraryManager;
import m19.app.exceptions.FileOpenFailedException;
import m19.exceptions.FailedToOpenFileException;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;

/**
 * 4.1.1. Open existing document.
 */
public class DoOpen extends Command<LibraryManager> {

  Input<String> _filename;

  /**
   * @param receiver
   */
  public DoOpen(LibraryManager receiver) {
    super(Label.OPEN, receiver);
    _filename = _form.addStringInput(Message.openFile());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
    try {
        _form.parse();
        _receiver.load(_filename.value());
    } catch (FailedToOpenFileException fnfe) {
        throw new FileOpenFailedException(fnfe.getName());
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    }
  }

}
