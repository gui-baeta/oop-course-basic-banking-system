package m19;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import m19.exceptions.*;
import m19.rule.DoubleRequest;
import m19.rule.IsReference;
import m19.rule.NoCopies;
import m19.rule.PriceCheck;
import m19.rule.RequestsAtSameTime;
import m19.rule.Rule;
import m19.rule.SituationCheck;
import m19.user.Request;
import m19.user.User;
import m19.user.notification.LoanNotification;
import m19.user.notification.Notification;
import m19.user.notification.ReturnNotification;
import m19.user.situation.Active;
import m19.work.Book;
import m19.work.DVD;
import m19.work.Work;

/**
 * Class that represents the library as a whole.
 */
public class Library implements Serializable {
    /** Serial number for serialization. */
    private static final long serialVersionUID = 201901101348L;

    private ArrayList<Work> _works;

    private LinkedHashMap<Integer, User> _users;

    private ArrayList<Rule> _rules;

    private int _numWorks;

    private int _numUsers;

    private int _numRequests;

    private int _time;

    /**
    * Constructor
    */
    public Library() {
        _works = new ArrayList<Work>();
        _users = new LinkedHashMap<Integer, User>();
        _rules = new ArrayList<Rule>();
        _numWorks = 0;
        _numUsers = 0;
        _numRequests = 0;
        _time = 0;

        _rules.add(new IsReference());
        _rules.add(new DoubleRequest());
        _rules.add(new SituationCheck());
        _rules.add(new RequestsAtSameTime());
        _rules.add(new PriceCheck());
        _rules.add(new NoCopies());
    }

    /**
    * Returns value of _works
    * @return
    */
    public ArrayList<Work> getWorks() {
    	return _works;
    }

    /**
    * Sets new value of works
    * @param works
    */
    public void setWorks(ArrayList<Work> works) {
    	_works = works;
    }

    /**
    * Returns value of _users
    * @return
    */
    public LinkedHashMap<Integer, User> getUsers() {
    	return _users;
    }

    /**
    * Sets new value of users
    * @param users
    */
    public void setUsers(LinkedHashMap<Integer, User> users) {
    	_users = users;
    }

    /**
    * Returns value of _rules
    * @return
    */
    public ArrayList<Rule> getRules() {
    	return _rules;
    }

    /**
    * Sets new value of rules
    * @param rules
    */
    public void setRules(ArrayList<Rule> rules) {
    	_rules = rules;
    }

    /**
    * Returns value of _numWorks
    * @return
    */
    public int getNumWorks() {
    	return _numWorks;
    }

    /**
    * Sets new value of numWorks
    * @param numWorks
    */
    public void setNumWorks(int numWorks) {
        _numWorks = numWorks;
    }

    /* Increments value o _numWorks by 1 */
    public void incrementNumWorks() {
        _numWorks++;
    }

    /**
    * Returns value of _numUsers
    * @return
    */
    public int getNumUsers() {
    	return _numUsers;
    }

    /**
    * Sets new value of numUsers
    * @param numUsers
    */
    public void setNumUsers(int numUsers) {
    	_numUsers = numUsers;
    }

    /* Increments value o _numUsers by 1 */
    public void incrementNumUsers() {
        _numUsers++;
    }

    /* Increments value o _numRequests by 1 */
    public void incrementNumRequests() {
        _numRequests++;
    }

    /**
    * Returns value of _numRequests
    * @return
    */
    public int getNumRequests() {
        return _numRequests;
    }

    /**
    * Returns value of _time
    * @return
    */
    public int getTime() {
    	return _time;
    }

    /**
    * Sets new value of time
    * @param time
    */
    public void setTime(int time) {
    	_time = time;
    }

    /**
    * shifts time/days by (shift) days
    * @param shift
    */
    public void incrementDay(int shift) {
        setTime(getTime() + shift);
    }

    /* updates the user situation of every user, if needed */
    public void updateUsersSituation() {
        for (Map.Entry<Integer, User> entry : getUsers().entrySet()) {
            User user = entry.getValue();

            // If user has debts, suspend him until he pays them
            if (user.hasDebts(getTime())) {
                if (user.getSituation() instanceof Active) {
                    user.updateSituation(false);
                }
            }
        }
    }

    /**
    * Registers user
    * @param userName
    * @param userMail
    */
    public int registerUser(String userName, String userMail)
    throws UserRegistrationNotValidException {

        if (userName.isEmpty() || userMail.isEmpty()) {
            throw new UserRegistrationNotValidException(userName, userMail);
        }

        User user = new User(getNumUsers(), userName, userMail);
        _users.put(user.getUserId(), user);
        incrementNumUsers();
        return getNumUsers() - 1;
    }

    /**
    * sorted ArrayList of users
    * @return users in a sorted manner
    */
    public ArrayList<User> getSortedUsers() {
        ArrayList<User> usersSorted = new ArrayList<>(_users.values());

        Collections.sort(usersSorted, Comparator.comparing(User::getName));

        return usersSorted;
    }

    /**
    * Returns user specified by userId
    * @param userId
    * @throws UserDoesntExistException
    * @return user or null if can't find user with specified userId
    */
    public User getUser(int userId) throws UserDoesntExistException {

        if (userId > getNumUsers() || userId < 0) {
            throw new UserDoesntExistException(userId);
        }

        return getUsers().get(userId);
    }

    /**
    * Returns user's notification ArrayList
    * @param userId
    * @throws UserDoesntExistException
    * @return chosen user _notification
    */
    public ArrayList<Notification> getUserNotifications(int userId)
    throws UserDoesntExistException {
        User user = getUser(userId);
        return user.getNotifications();
    }

    /**
    * Deletes every entry in the user's _notification
    * @param userId
    * @throws UserDoesntExistException
    */
    public void clearUserNotifications(int userId)
    throws UserDoesntExistException {
        User user = getUser(userId);

        user.clearNotifications();
    }

    /**
    * Registers work
    * @param type
    * @param title
    * @param nCopies
    * @param value
    * @param category
    * @param dir_auth
    * @param IGAC_ISBN
    */
    public void registerWork(String type, String title, String nCopies,
    String value, String category, String dir_auth, String IGAC_ISBN) {
    	switch (type) {
    		case "DVD":
    			DVD dvd = new DVD(getNumWorks(), title, nCopies, value,
                category, dir_auth, IGAC_ISBN);
    			incrementNumWorks();
    			_works.add(dvd);
    			break;

    		case "BOOK":
    			Book book = new Book(getNumWorks(), title, nCopies, value,
                category, dir_auth, IGAC_ISBN);
    			incrementNumWorks();
    			_works.add(book);
    			break;
    	}
    }

    /**
    * Creates a request by user for a work
    * @param userId
    * @param workId
    * @throws UserDoesntExistException
    * @throws WorkDoesntExistException
    * @throws RequestRuleFailedException
    * @return duedate for request
    */
    public int requestWork(int userId, int workId)
    throws UserDoesntExistException, WorkDoesntExistException,
    RequestRuleFailedException, RequestRule3FailedException {

        User user = getUser(userId);
        Work work = getWork(workId);

        checkAllRules(user, work);

        Request newRequest = new Request(getNumRequests(), work, user, _time);

        user.getRequests().add(newRequest);
        incrementNumRequests();

        work.decrementNCopiesLeft();

        notifyRequest(work);

        return newRequest.getDueDate();
    }

    /**
    * User returns work to library
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

        User user = getUser(userId);
        Work work = getWork(workId);

        Request rq = getRequest(userId, workId);

        if (rq.dueDateCheck(getTime())) {
            user.getRequests().remove(rq);
            user.updateStatus(true);

            work.incrementNCopiesLeft();

            user.updateSituation(true);

            notifyReturn(work);
        } else {
            int fine = rq.calculateFine(getTime());

            user.updateStatus(false);

            user.addDebts(fine);

            rq.setReturnedState(true);

            work.incrementNCopiesLeft();

            user.updateSituation(false);

            notifyReturn(work);

            throw new FineToPayException(userId, workId, user.getDebts(), rq);
        }
    }

    /**
    * Returns work specified by workId
    * @param workId
    * @throws WorkDoesntExistException
    * @return work
    */
    public Work getWork(int workId) throws WorkDoesntExistException {
        try {
            return _works.get(workId);
        } catch (IndexOutOfBoundsException ioobe) {
            throw new WorkDoesntExistException(workId);
        }
    }

    /**
    * Searches specified work for search parameter token
    * @param work
    * @param token
    * @return Found token in specified work
    */
    public ArrayList<Work> searchWork(String token) {

        ArrayList<Work> filteredWorks = new ArrayList<Work>();

        for (Work w : getWorks()) {
            if (w.compareToWork(token)) {
                filteredWorks.add(w);
            }
        }
        return filteredWorks;
    }

    /**
    * Adds new return notification to user's notifications
    * @param work
    */
    public void notifyReturn(Work work) {
        for (Map.Entry<Integer, User> entry :
        work.getUsersInterested().entrySet()) {

            User user = entry.getValue();
            user.addNotification(new ReturnNotification(work));
        }
    }

    /**
    * Adds new LoanNotification to user's _notification
    * @param work
    */
    public void notifyRequest(Work work) {
        for (Map.Entry<Integer, User> entry :
        work.getUsersInterested().entrySet()) {

            User user = entry.getValue();
            user.addNotification(new LoanNotification(work));
        }
    }

    /**
    * User becomes interested in work
    * @param userId
    * @param workId
    * @throws UserDoesntExistException
    * @throws WorkDoesntExistException
    */
    public void addInterest(int userId, int workId)
    throws WorkDoesntExistException, UserDoesntExistException {
        Work work = getWork(workId);
        User user = getUser(userId);

        work.addUserInterested(user);
    }

    /**
    * Returns user's request for work
    * @param userId
    * @param workId
    * @throws UserDoesntExistException
    * @throws UserDidntBorrowWorkException
    * @return request or null
    */
    public Request getRequest(int userId, int workId)
    throws UserDoesntExistException, UserDidntBorrowWorkException {
        User user = getUser(userId);
        for (Request rq : user.getRequests()) {
            if (rq.getWorkRequested().getWorkId() == workId && !rq.hasBeenReturned()) {
                return rq;
            }
        }
        throw new UserDidntBorrowWorkException(userId, workId);
    }

    /**
    * Checks if all library rules are beeing followed
    * @param user
    * @param work
    * @throws RequestRuleFailedException
    */
    public void checkAllRules(User user, Work work)
    throws RequestRuleFailedException, RequestRule3FailedException {

        for (Rule rule : getRules()) {
            if (rule.evaluate(user, work)) {
                if (rule.getRuleId() == 3) {
                    throw new RequestRule3FailedException(user.getUserId(),
                    work.getWorkId());
                } else {
                    throw new RequestRuleFailedException(user.getUserId(),
                    work.getWorkId(), rule.getRuleId());
                }
            }
        }
    }

    /**
    * User pays fine
    * @param userId
    * @throws UserDoesntExistException
    * @throws UserIsntSuspendedException
    */
    public void payFine(int userId)
    throws UserDoesntExistException, UserIsntSuspendedException {
        User user = getUser(userId);

        if (user.getSituation() instanceof Active) {
            throw new UserIsntSuspendedException(userId);
        }

        Iterator<Request> itr = user.getRequests().iterator();
        while (itr.hasNext()) {
            Request rq = itr.next();
            if (rq.hasBeenReturned()) {
                itr.remove();
            }
        }

        if (!user.hasDebts(getTime())) {
            user.updateSituation(true);
        }

        user.setDebts(0);
    }

    /**
    * Read the text input file at the beginning of the program and populates...
    * ...the instances of the various possible types (books, DVDs, users).
    *
    * @param filename
    *          name of the file to load
    * @throws BadEntrySpecificationException
    * @throws IOException
    */
    public void importFile(String filename)
    throws BadEntrySpecificationException, IOException {

        FileReader fileReader = new FileReader(filename);
        BufferedReader file_buffer = new BufferedReader(fileReader);

        String line = file_buffer.readLine();

        while (line != null) {
            String[] lineparts = line.split(":");
            switch (lineparts[0]) {
                case "DVD":
                case "BOOK":
    				registerWork(lineparts[0], lineparts[1], lineparts[6],
                    lineparts[3], lineparts[4], lineparts[2], lineparts[5]);
                    break;

                case "USER":
                    try {
                        registerUser(lineparts[1], lineparts[2]);
                    } catch(UserRegistrationNotValidException e) {}
                    break;

                default:
                    throw new BadEntrySpecificationException(filename);
            }

            line = file_buffer.readLine();
        }
        file_buffer.close();
    }
}
