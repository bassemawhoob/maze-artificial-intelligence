package project.save_westeros;

import project.abstracts.*;

public class Main {
	public static void main(String[] args) {
		SaveWesteros sw = new SaveWesteros();
		sw.GenGrid();
		search(sw, "BF", true);
	}

	public static void search(SearchProblem sw, String strategy, boolean visualize) {
		Node solution = null;
		System.out.println("Problem - Initial state: ");
		System.out.println(sw.getInitialState());
		switch (strategy) {
		case "BF":
			solution = new BreadthFirstTreeSearch().solve(sw);
			break;
		case "DF":
			solution = new DepthFirstTreeSearch().solve(sw);
			break;
		case "ID":
			solution = new IterativeDeepeningTreeSearch().solve(sw);
			break;
		case "UC":
			solution = new UniformCostTreeSearch().solve(sw);
			break;
		case "GR":
			// You can choose from 2 heuristic functions in the Node class
			solution = new GreedyTreeSearch().solve(sw);
			break;
		case "AS":
			// You can choose from 2 heuristic functions in the Node class
			solution = new AStarTreeSearch().solve(sw);
			break;
		default:
			System.out.println("Error: Please enter a valid search strategy {'BF', 'DF', 'ID', 'UC', 'GR', 'AS'}");
			break;
		}
		if (solution != null) {
			System.out.println("Solution's path cost: " + solution.getPathCost());
			System.out.println("Steps to reach goal: " + solution.pathToString());
			System.out.println("=========");
			if (visualize) {
				System.out.println(solution.visualizePath());
			}
		}
	}
}
