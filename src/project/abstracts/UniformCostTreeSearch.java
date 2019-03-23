package project.abstracts;

import java.util.Collection;
import java.util.LinkedList;
import java.util.TreeSet;

public class UniformCostTreeSearch extends SearchProcedure {

	@Override
	public Node solve(SearchProblem problem) {
		System.out.println("Solution to problem using uniform cost search : ");
		queue = initQueue();
		queue.addAll(expand(new Node(problem.getInitialState()), problem));
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
					queue.addAll(expand(node, problem));
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

	// Very expensive operation
//	private void insertSorted(Collection<Node> nodes) {
//		for (Node node : nodes) {
//			double cost = node.getPathCost();
//			if (queue.size() == 0) {
//				queue.add(node);
//			} else if (queue.get(0).getPathCost() > cost) {
//				queue.add(0, node);
//			} else if (queue.get(queue.size() - 1).getPathCost() < cost) {
//				queue.add(queue.size(), node);
//			} else {
//				int index = 0;
//				while (queue.get(index).getPathCost() < cost)
//					index++;
//				queue.add(index, node);
//			}
//		}
//	}

}
