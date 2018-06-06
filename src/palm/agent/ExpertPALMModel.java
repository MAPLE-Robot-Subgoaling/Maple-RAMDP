package palm.agent;

import burlap.mdp.core.action.Action;
import burlap.mdp.core.state.State;
import burlap.mdp.singleagent.environment.EnvironmentOutcome;
import burlap.mdp.singleagent.model.FactoredModel;
import burlap.mdp.singleagent.model.TransitionProb;
import utilities.ConstantDiscountProvider;
import utilities.DiscountProvider;

import java.util.List;

public class ExpertPALMModel extends PALMModel {

    private FactoredModel baseModel;
    protected DiscountProvider discountProvider;

    public ExpertPALMModel(FactoredModel baseModel, double gamma){
        this.baseModel = baseModel;
        this.discountProvider = new ConstantDiscountProvider(gamma);
    }

    @Override
    public boolean terminal(State s) {
        return baseModel.terminal(s);
    }

    @Override
    public List<TransitionProb> transitions(State s, Action a) {
        return baseModel.transitions(s, a);
    }

    @Override
    public void updateModel(EnvironmentOutcome result, int stepsTaken) {}

    @Override
    public DiscountProvider getDiscountProvider() {
        return discountProvider;
    }

}