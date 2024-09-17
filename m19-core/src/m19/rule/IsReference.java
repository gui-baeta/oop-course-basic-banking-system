package m19.rule;

import m19.user.User;
import m19.work.Work;
import m19.work.category.Reference;

public class IsReference extends Rule {

    private static final long serialVersionUID = 201901101348L;

    private int _ruleId = 5;

    public int getRuleId() {
        return _ruleId;
    }

    public boolean evaluate(User user, Work work) {
        return work.getCategory() instanceof Reference;
    }
}
