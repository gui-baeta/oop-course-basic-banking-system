package m19.user;

import java.io.Serializable;
import java.util.ArrayList;

import m19.user.notification.Notification;
import m19.user.situation.Active;
import m19.user.situation.Situation;
import m19.user.situation.Suspended;
import m19.user.status.Abiding;
import m19.user.status.Normal;
import m19.user.status.Status;
import m19.user.status.Unreliable;

public class User implements Serializable {

    private static final long serialVersionUID = 201901101348L;

    private int _userId;

    private String _name;

    private String _email;

    private ArrayList<Notification> _notifications;

    private ArrayList<Request> _requests;

    private int _debts;

    private Situation _situation;

    private Status _status;

    /**
    * Constructor
    * @param userId
    * @param name    
    * @param email
    */
    public User(int userId, String name, String email) {
        _userId = userId;
        _name = name;
        _email = email;

        _notifications = new ArrayList<Notification>();
        _requests = new ArrayList<Request>();

        _situation = new Active();
        _status = new Normal();
    }

    /**
    * Sets new value of _userId
    * @param userId
    */
    public void setUserId(int userId) {
        _userId = userId;
    }

    /**
    * Returns value of _userId
    * @return
    */
    public int getUserId() {
        return _userId;
    }

    /**
    * Sets new value of _name
    * @param name
    */
    public void setName(String name) {
        _name = name;
    }

    /**
    * Returns value of _name
    * @return
    */
    public String getName() {
        return _name;
    }

    /**
    * Sets new value of _email
    * @param email
    */
    public void setEmail(String email) {
        _email = email;
    }

    /**
    * Returns value of _email
    * @return
    */
    public String getEmail() {
        return _email;
    }

    /**
    * Sets new value of _notifications
    * @param notifications
    */
    public void setNotifications(ArrayList<Notification> notifications) {
        _notifications = notifications;
    }

    /**
    * Clears _notifications
    */
    public void clearNotifications() {
        _notifications.clear();
    }

    /**
    * Add new notification to _notifications
    * @param notification
    */
    public void addNotification(Notification notification) {
        _notifications.add(notification);
    }

    /**
    * Returns value of _notifications
    * @return
    */
    public ArrayList<Notification> getNotifications() {
        return _notifications;
    }

    /**
    * Returns value of _requests
    * @return
    */
    public ArrayList<Request> getRequests() {
        return _requests;
    }

    /**
    * Returns value of _debts
    * @return
    */
    public int getDebts() {
        return _debts;
    }

    /**
    * Sets new value of _debts
    * @param debts
    */
    public void setDebts(int debts) {
        _debts = debts;
    }

    /**
    * Adds debt to existing _debts
    * @param userId
    */
    public void addDebts(int debt) {
        setDebts(getDebts() + debt);
    }

    /**
    * checks if user has debts 
    * @param currentTime
    * @return boolean 
    */
    public boolean hasDebts(int currentTime) {
        int fine = 0;

        for (Request rq : getRequests()) {
            if (!rq.hasBeenReturned()) {
                fine += rq.calculateFine(currentTime);
            }
        }

        return fine > 0;
    }

    /**
    * Sets new value of _situation
    * @param situation
    */
    public void setSituation(Situation situation) {
        _situation = situation;
    }

    /**
    * Returns value of _situation
    * @return
    */
    public Situation getSituation() {
        return _situation;
    }

    /**
    * Sets new value of _status
    * @param status
    */
    public void setStatus(Status status) {
        _status = status;
    }

    /**
    * Returns value of _status
    * @return
    */
    public Status getStatus() {
        return _status;
    }

    /**
    * Updates user status to match current circumnstances
    * @param inTime
    */
    public void updateStatus(boolean inTime) {
        getStatus().updateStreakTracker(inTime);

        if (getStatus().getStreakTracker() == -3) {
            setStatus(new Unreliable());
        } else if (getStatus() instanceof Unreliable) {
            if (getStatus().getStreakTracker() == 3) {
                setStatus(new Normal());
            }
        } else if (getStatus().getStreakTracker() == 5) {
            setStatus(new Abiding());
        } else if (getStatus() instanceof Abiding) {
            if (getStatus().getStreakTracker() < 5) {
                setStatus(new Normal());
            }
        }
    }

    /**
    * Updates user situation to match current circumnstances
    * @param behaved
    */
    public void updateSituation(boolean behaved) {
        if (behaved) {
            setSituation(new Active());
        } else {
            setSituation(new Suspended());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        User u = (User) o;
        return this.getUserId() == u.getUserId() &&
               this.getName().equals(u.getName()) &&
               this.getEmail().equals(u.getEmail());
    }

    @Override
    public int hashCode() {
        return getUserId();
    }

	/**
	* Create string representation of User for printing
	* @return
	*/
	@Override
	public String toString() {
        if (getSituation() instanceof Suspended) {
            return getUserId() + " - " + getName() + " - " + getEmail() + " - " + getStatus() + " - " + getSituation() + " - EUR " + getDebts();
        } else {
            return getUserId() + " - " + getName() + " - " + getEmail() + " - " + getStatus() + " - " + getSituation();
        }
    }
}
