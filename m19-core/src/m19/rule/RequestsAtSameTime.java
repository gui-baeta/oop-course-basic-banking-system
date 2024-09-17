package m19.rule;

import m19.user.User;
import m19.user.status.Abiding;
import m19.user.status.Unreliable;
import m19.work.Work;

public class RequestsAtSameTime extends Rule {

    private static final long serialVersionUID = 201901101348L;

    private int _ruleId = 4;

    public int getRuleId() {
        return _ruleId;
    }

    public boolean evaluate(User user, Work work) {
        int works_borrowed = user.getRequests().size();

        if(user.getStatus() instanceof Abiding) {
            return works_borrowed >= 5;
        }
        else if (user.getStatus() instanceof Unreliable) {
            return works_borrowed >= 1;
        }
        else {
            return works_borrowed >= 3;
        }
    }
}
