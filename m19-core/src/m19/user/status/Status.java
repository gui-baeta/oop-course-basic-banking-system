package m19.user.status;

import java.io.Serializable;

public abstract class Status implements Serializable {

    private static final long serialVersionUID = 201901101348L;

    private int _streakTracker = 0;

    /**
    * Sets new value of _streakTracker
    * @param streakTracker
    */
    public void setStreakTracker(int streakTracker) {
        _streakTracker = streakTracker;
    }

    /**
    * Returns value of _streakTracker
    * @return
    */
    public int getStreakTracker() {
        return _streakTracker;
    }

    /** increments _streakTracker */
    public void incrementStreakTracker() {
        if (getStreakTracker() > 0) {
            if (_streakTracker < 5) {
                _streakTracker++;
            }
        } else {
            _streakTracker = 1;
        }
    }

    /** decrements _streakTracker */
    public void decrementStreakTracker() {
        if (getStreakTracker() > 0) {
            _streakTracker = -1;
        } else {
            if (_streakTracker > -3) {
                _streakTracker--;
            }
        }
    }

    /**
    * Updates _streakTracker
    * @param inTime
    */
    public void updateStreakTracker(boolean inTime) {
        if (inTime) {
            incrementStreakTracker();
        } else {
            decrementStreakTracker();
        }
    }

    /**
    * Finds how many days after borrowing user can wait before missing dueDate
    * @param nCopies
    * @return days until return
    */
    public abstract int availableDays(int nCopies);
}
