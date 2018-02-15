package hierarchy.framework;

import burlap.mdp.auxiliary.StateMapping;
import burlap.mdp.core.action.Action;
import burlap.mdp.core.action.ActionType;
import burlap.mdp.core.oo.ObjectParameterizedAction;
import burlap.mdp.core.oo.propositional.PropositionalFunction;
import burlap.mdp.core.oo.state.OOState;
import burlap.mdp.core.state.State;
import burlap.mdp.singleagent.model.RewardFunction;
import burlap.mdp.singleagent.oo.OOSADomain;
import ramdp.agent.RAMDPModel;

import java.util.Arrays;

public class NonprimitiveTask extends Task {
    //tasks which are not at the base of the hierarchy

    // default rewardTotal used in nonprimitive task's pseudo-rewardTotal function
    // a slightly positive rewardTotal provides a smooth gradient for learning discounts in a multi-time model
    public static double DEFAULT_REWARD = 0.0;//0.000001;

	protected GoalFailTF goalFailTF;
	protected GoalFailRF goalFailRF;
	
	//used for hierarchies with abstractions
	/**
	 * create a nunprimitive task
	 * @param children the subtasks
	 * @param aType the set of actions this task represents in its parent task's domain
	 * @param abstractDomain the domain this task executes actions in
	 * @param map the state abstraction function into the domain
	 * @param fail the failure PF 
	 * @param compl the completion PF
	 */
	public NonprimitiveTask(Task[] children, ActionType aType, OOSADomain abstractDomain, StateMapping map,
			PropositionalFunction fail, PropositionalFunction compl, double defaultReward) {
		super(children, aType, abstractDomain, map);
		this.goalFailTF = new GoalFailTF(compl, null, fail, null);
		this.goalFailRF = new GoalFailRF(this.goalFailTF, defaultReward);
	}

	/**
	 * create a nunprimitive taks
	 * @param children the subtasks
	 * @param aType the set of actions this task represents in its parent task's domain
	 * @param map the state abstraction function into the domain
	 * @param fail the failure PF
	 * @param compl the completion PF
	 */
	public NonprimitiveTask(Task[] children, ActionType aType, StateMapping map,
							PropositionalFunction fail, PropositionalFunction compl) {
		this(children, aType, null, map, fail, compl, DEFAULT_REWARD);
	}
	//used for hierarchies with no abstraction
	/**
	 * create a nunprimitive taks
	 * @param children the subtasks
	 * @param aType the set of actions this task represents in its parent task's domain
	 * @param fail the failure PF
	 * @param compl the completion PF
	 */
	public NonprimitiveTask(Task[] children, ActionType aType,
			PropositionalFunction fail, PropositionalFunction compl) {
		this(children, aType, null, null, fail, compl, DEFAULT_REWARD);
	}

	
	@Override
	public boolean isPrimitive() {
		return false;
	}
	
	/**
	 * uses the defined rewardTotal function to assign rewardTotal to states
	 * @param s the original state that is being transitioned from
	 * @param a the action associated with the grounded version of this task
	 * @param sPrime the next state that is being transitioned into
	 * @return the rewardTotal assigned to s by the rewardTotal function
	 */
	@Override
	public double reward(State s, Action a, State sPrime){
		return goalFailRF.reward(s, a, sPrime);
	}
	
	/**
	 * customise the rewardTotal function
	 * @param rf the rewardTotal function which should take in a state and
	 * grounded action
	 */
	public void setRF(GoalFailRF rf){
		this.goalFailRF = rf;
	}

	@Override
	public boolean isFailure(State s, Action a) {
	    String[] params = parseParams(a);
		boolean atFailure = goalFailTF.atFailure(s, params);
		return atFailure;
	}
	
	@Override
	public boolean isComplete(State s, Action a){
        String[] params = parseParams(a);
	    boolean atGoal = goalFailTF.atGoal(s, params);
	    return atGoal;
	}
    public static String[] parseParams(Action action) {
        String[] params = null;
        if (action instanceof ObjectParameterizedAction) {
            params = ((ObjectParameterizedAction) action).getObjectParameters();
        } else {
            params = new String[]{StringFormat.parameterizedActionName(action)};
        }
        return params;
    }
}
