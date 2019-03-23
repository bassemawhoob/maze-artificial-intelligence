package project.abstracts;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import project.save_westeros.Operator;
import project.save_westeros.State;

public abstract class SearchProcedure {

	Collection<Node> queue;

	public abstract Node solve(SearchProblem problem);

	public abstract Collection<Node> initQueue();
	
	public Collection<Node> expand(Node node, SearchProblem problem) {
		Collection<Node> nodes = new LinkedList<Node>();
		Collection<Operator> operators = problem.getOperators(node.getState());
		for (Operator operator : operators) {
			State next = problem.getNextState(node.getState(), operator);
			boolean ancestorFound = false;
			List<Node> ancestors = node.getPathFromRoot();
			for (Node ancestor : ancestors) {
				if (ancestor.getState().equals(next)) {
					ancestorFound = true;
					break;
				}
			}
			if (!ancestorFound) {
				nodes.add(new Node(next, node, operator, problem.getPathCost(node.getState(), operator, next)));
			}
		}
		return nodes;
	}

	public abstract Node chooseLeafNode(Collection<Node> queue, SearchProblem problem);

}