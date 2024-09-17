package m19;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import m19.exceptions.*;
import m19.rule.Rule;
import m19.user.User;
import m19.user.notification.Notification;
import m19.work.Work;

/**
 * The façade class.
 */
public class LibraryManager {

    private Library _library; // Library

    private String _filename; // Name of the save file

    private boolean _modified; // Flag that says if Library has been modified

    public LibraryManager() { // Library Manager Constructor
        _library = new Library();
        _filename = null;
        _modified = true;
    }

    /**
    * Get current time/day
    * @return Current time/day
    */
    public int getTime() {
        return _library.getTime();
    }

    /**
    * Update time/day with specified time shift and update all users...
    * ...situation in the process
    * @param shift
    */
    public void incrementDay(int shift) {
        if (shift > 0) {
            _library.incrementDay(shift);

            _library.updateUsersSituation();

            _modified = true;
        }
    }

    /**
    * Update filename with specified filename
    * @param filename
    */
    public void setFilename(String filename) {
        _filename = filename;
    }

    /**
    * Get filename
    * @return Filename
    */
    public String getFilename() {
        return _filename;
    }

    /**
    * Create new user with specified name and email
    * @param userName
    * @param userMail
    * @return New user id
    */
    public int registerUser(String userName, String userMail)
    throws UserRegistrationNotValidException {
        _modified = true;
        return _library.registerUser(userName, userMail);
    }

    /**
    * Get user with id - userId
    * @param userId
    * @throws UserDoesntExistException
    * @return Specific user
    */
    public User getUser(int userId)
    throws UserDoesntExistException {

        return _library.getUser(userId);
    }

    /**
    * Get all users in a sorted manner
    * @return ArrayList of users
    */
    public ArrayList<User> getSortedUsers() {
        return _library.getSortedUsers();
    }

    /**
    * Get all notifications of specified user with id - userId
    * @param userId
    * @throws UserDoesntExistException
    * @return ArrayList of notifications
    */
    public ArrayList<Notification> getUserNotifications(int userId)
    throws UserDoesntExistException {

        return _library.getUserNotifications(userId);
    }

    /**
    * Clear user notifications
    * @param userId
    */
    public void clearUserNotifications(int userId) {
        try {
            _library.clearUserNotifications(userId);
            _modified = true;
        } catch(UserDoesntExistException e) {}
    }

    /**
    * Get array list of works
    * @return ArrayList of works
    */
    public ArrayList<Work> getWorks() {
		return _library.getWorks();
	}

    /**
    * Get specific work with id - workId
    * @param workId
    * @throws WorkDoesntExistException
    * @return work specified by workId
    */
    public Work getWork(int workId)
    throws WorkDoesntExistException {

       return _library.getWork(workId);
    }

    /**
    * Get total number of works
    * @return total number of works
    */
    public int getNumWorks() {
        return _library.getNumWorks();
    }

    /**
    * Search through works with search parameter specified by token
    * @param userId
    * @param token
    * @return work that contains string token
    */
    public ArrayList<Work> searchWork(String token) {
        return _library.searchWork(token);
    }

    /**
    * Request work asked by user
    * @param userId
    * @param workId
    * @throws UserDoesntExistException
    * @throws WorkDoesntExistException
    * @throws RequestRuleFailedException
    * @return due date to return requested work
    */
    public int requestWork(int userId, int workId)
    throws UserDoesntExistException, WorkDoesntExistException,
    RequestRuleFailedException, RequestRule3FailedException {

        _modified = true;
        return _library.requestWork(userId, workId);
    }

    /**
    * Return work held by user
    * @param userId
    * @param workId
    * @throws UserDoesntExistException
    * @throws WorkDoesntExistException
    * @throws UserDidntBorrowWorkException
    * @throws FineToPayException
    */
    public void returnWork(int userId, int workId)
    throws UserDoesntExistException, UserDidntBorrowWorkException,
    WorkDoesntExistException, FineToPayException {

        _library.returnWork(userId, workId);
        _modified = true;
    }

    /**
    * Pay user fine
    * @param userId
    * @throws UserDoesntExistException
    * @throws UserIsntSuspendedException
    */
    public void payFine(int userId)
    throws UserDoesntExistException, UserIsntSuspendedException {

        _library.payFine(userId);
        _modified = true;
    }

    public void payFine(int userId, boolean wantsToPayFine) {
        if (wantsToPayFine) {
            try {
                _library.payFine(userId);
                _modified = true;
            } catch(UserDoesntExistException | UserIsntSuspendedException e) {}
        }
    }

    /**
    * If user shows interest to a particular work, bind user to work
    * @param userId
    * @param workId
    */
    public void addInterest(int userId, int workId, boolean wantToBeNotified) {
        if (wantToBeNotified) {
            try {
                _library.addInterest(userId, workId);
                _modified = true;
            } catch(WorkDoesntExistException | UserDoesntExistException e) {}
        }
    }

    /**
    * Saves library state to file specified by saved _filename
    * @throws MissingFileAssociationException
    * @throws IOException
    * @throws FileNotFoundException
    */
    public void save()
    throws MissingFileAssociationException, IOException, NullPointerException {

        if (!_modified) {
            return;
        }
        if (canSave()) {
            FileOutputStream fileOutS = new FileOutputStream(_filename);
            BufferedOutputStream bufOutS = new BufferedOutputStream(fileOutS);
            ObjectOutputStream out = new ObjectOutputStream(bufOutS);
            out.writeObject(_library);
            out.close();

            _modified = false;

            return;
        }
        throw new MissingFileAssociationException();
    }

    /**
    * Saves library state to file specified by given filename
    * @param filename
    * @throws MissingFileAssociationException
    * @throws IOException
    */
    public void saveAs(String filename)
    throws MissingFileAssociationException, IOException, NullPointerException {

        setFilename(filename);
        save();
    }

    public boolean canSave() {
        if (getFilename() == null) {
            return false;
        }
        return true;
    }

    /**
    * @param filename
    * @throws FailedToOpenFileException
    * @throws IOException
    * @throws ClassNotFoundException
    */
    public void load(String filename)
    throws FailedToOpenFileException, ClassNotFoundException {

        try {
            FileInputStream fileInS = new FileInputStream(filename);
            BufferedInputStream buffInS = new BufferedInputStream(fileInS);
            ObjectInputStream in = new ObjectInputStream(buffInS);
            setFilename(filename);

            _library = (Library) in.readObject();
            in.close();
        }
        catch (IOException e) {
            throw new FailedToOpenFileException(filename);
        }
    }

    /**
    * @param datafile
    * @throws ImportFileException
    */
    public void importFile(String datafile)
    throws ImportFileException {

        try {
            _library.importFile(datafile);
            _modified = true;
        } catch (IOException | BadEntrySpecificationException e) {
            throw new ImportFileException(e);
        }
    }
}
