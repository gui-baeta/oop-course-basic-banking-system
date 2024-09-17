package m19.rule;

import m19.user.User;
import m19.work.Work;

public class NoCopies extends Rule {

    private static final long serialVersionUID = 201901101348L;

    private int _ruleId = 3;

    public int getRuleId() {
        return _ruleId;
    }

    public boolean evaluate(User user, Work work) {
        return work.getNCopiesLeft() == 0;
    }
}
