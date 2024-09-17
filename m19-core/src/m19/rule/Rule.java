package m19.rule;

import java.io.Serializable;

import m19.user.User;
import m19.work.Work;

public abstract class Rule implements Serializable {

    private static final long serialVersionUID = 201901101348L;

    public abstract int getRuleId();

    /**
     * Returns true if rule is broken. False if not. 
     * @param user
     * @param work
     * @return boolean
     */
    public abstract boolean evaluate(User user, Work work);
}
