package m19.rule;

import m19.user.User;
import m19.user.situation.Suspended;
import m19.work.Work;

public class SituationCheck extends Rule {

    private static final long serialVersionUID = 201901101348L;

    private int _ruleId = 2;

    public int getRuleId() {
        return _ruleId;
    }

    public boolean evaluate(User user, Work work) {
        return user.getSituation() instanceof Suspended;
    }

}
