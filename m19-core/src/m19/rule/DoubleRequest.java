package m19.rule;

import m19.user.User;
import m19.work.Work;

public class DoubleRequest extends Rule {

    private static final long serialVersionUID = 201901101348L;

    private int _ruleId = 1;

    public int getRuleId() {
        return _ruleId;
    }

    public boolean evaluate(User user, Work work) {

        if (user.getRequests().contains(work)) {
            return true;
        }
        return false;
    }
}
