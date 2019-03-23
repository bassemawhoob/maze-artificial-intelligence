package project.abstracts;

import java.util.Collection;
import java.util.LinkedList;

public class IterativeDeepeningTreeSearch {

	public Node solve(SearchProblem problem) {
		int depthLimit = 0;
		Node solution = null;
		while (solution == null) {
			DepthLimitedTreeSearch dlt = new DepthLimitedTreeSearch();
			dlt.setDepthLimit(depthLimit);
			solution = dlt.solve(problem);
			depthLimit++;
		}
		System.out.println("Solution found at depth: " + (depthLimit - 1));
		return solution;
	}
}
