package m19.rule;

import m19.user.User;
import m19.user.status.Abiding;
import m19.work.Work;

public class PriceCheck extends Rule {

    private static final long serialVersionUID = 201901101348L;

    private int _ruleId = 6;

    public int getRuleId() {
        return _ruleId;
    }

    public boolean evaluate(User user, Work work) {
        return (work.getValue() > 25) && !(user.getStatus() instanceof Abiding);
    }
}
