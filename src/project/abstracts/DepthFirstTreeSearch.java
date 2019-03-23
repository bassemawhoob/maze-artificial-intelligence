package project.abstracts;

import java.util.Collection;
import java.util.LinkedList;

public class DepthFirstTreeSearch extends SearchProcedure {

	public Node solve(SearchProblem problem) {
		System.out.println("Solution to problem using depth first : ");
		queue = initQueue();
		((LinkedList<Node>) queue).addAll(0, expand(new Node(problem.getInitialState()), problem));
		boolean done = false;
		Node solution = null;
		while (!done) {
			if (queue.isEmpty()) {
				System.out.println("No more nodes in queue => FAILURE");
				done = true;
			} else {
				Node node = chooseLeafNode(queue, problem);
				if (problem.isGoal(node.getState())) {
					System.out.println("Goal node reached => SUCCESS");
					solution = node;
					done = true;
				} else {
					((LinkedList<Node>) queue).addAll(0, expand(node, problem));
				}
			}
		}
		return solution;
	}

	public Collection<Node> initQueue() {
		return new LinkedList<Node>();
	}

	public Node chooseLeafNode(Collection<Node> queue, SearchProblem problem) {
		return ((LinkedList<Node>) queue).removeFirst();
	}

}