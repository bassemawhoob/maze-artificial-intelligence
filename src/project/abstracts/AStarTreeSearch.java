package project.abstracts;

import java.util.Collection;
import java.util.TreeSet;

public class AStarTreeSearch extends SearchProcedure {

	@Override
	public Node solve(SearchProblem problem) {		
		System.out.println("Solution to problem using A* : ");
		queue = initQueue();
		queue.addAll(Node.setSearchStrategy(expand(new Node(problem.getInitialState()), problem), 2));
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
					queue.addAll(Node.setSearchStrategy(expand(node, problem), 2));
				}
			}
		}
		return solution;
	}

	@Override
	public Collection<Node> initQueue() {
		return new TreeSet<Node>();
	}

	@Override
	public Node chooseLeafNode(Collection<Node> queue, SearchProblem problem) {
		return ((TreeSet<Node>) queue).pollFirst();
	}

}
