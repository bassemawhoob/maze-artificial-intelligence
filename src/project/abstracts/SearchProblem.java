package project.abstracts;

import java.util.List;
import project.save_westeros.Operator;
import project.save_westeros.State;

public interface SearchProblem {
    public State getInitialState();
    public boolean isGoal(State state);
	public List<Operator> getOperators(State state);
    public State getNextState(State state, Operator operator);
    public int getPathCost(State start_state, Operator operator, State dest_state);
}
