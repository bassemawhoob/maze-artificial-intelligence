package project.abstracts;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import project.save_westeros.Operator;
import project.save_westeros.State;

public class Node implements Comparable<Node> {
	private State state;
	private Node parent;
	private Operator operator;
	private int depth;
	private int pathCost;

	// To determine how to the value of compareTo function is calculated {0 =
	// Uniform cost (least path cost), 1 = Greedy (least heuristic function), 2 = A*
	// (least sum of path cost and heuristic function)}
	private int searchStrategy;
	// Chooses which heuristic function to use {1 or 2}
	private final int heuristicFunction = 2;

	public Node(State state) {
		this.state = state;
		this.pathCost = 0;
		this.depth = 0;
		this.parent = null;
	}

	public Node(State state, Node parent, Operator operator, int stepCost) {
		this(state);
		this.parent = parent;
		this.operator = operator;
		this.pathCost = parent.pathCost + stepCost;
		this.depth = parent.depth + 1;
	}

	public List<Node> getPathFromRoot() {
		List<Node> path = new ArrayList<Node>();
		Node current = this;
		while (!current.isRoot()) {
			path.add(0, current);
			current = current.getParent();
		}
		// ensure the root node is added
		path.add(0, current);
		return path;
	}

	public String pathToString() {
		String t = "";
		List<Node> nodes = getPathFromRoot();
		for (int i = 0; i < nodes.size(); i++) {
			if (nodes.get(i).getOperator() != null) {
				switch (nodes.get(i).getOperator()) {
				case UP:
					t += "⇧ ";
					break;
				case DOWN:
					t += "⇩ ";
					break;
				case LEFT:
					t += "⇦ ";
					break;
				case RIGHT:
					t += "⇨ ";
					break;
				case KILL:
					t += "X ";
					break;
				default:
					break;
				}
			}
		}
		return t;
	}

	public String visualizePath() {
		String s = "";
		List<Node> nodes = getPathFromRoot();
		for (int i = 0; i < nodes.size(); i++) {
			s += "Action : " + nodes.get(i).getOperator() + "\n";
			s += "State  : \n" + nodes.get(i).getState() + "\n";
		}
		return s;
	}

	// Getters
	public State getState() {
		return state;
	}

	public Node getParent() {
		return parent;
	}

	public Operator getOperator() {
		return operator;
	}

	public double getPathCost() {
		return pathCost;
	}

	public boolean isRoot() {
		return parent == null;
	}

	public int getDepth() {
		return depth;
	}

	@Override
	public int compareTo(Node o) {
		switch (this.searchStrategy) {
		// Uniform cost search
		case 0:
			int g = this.pathCost - o.pathCost;
			return g != 0 ? g : 1;
		// Greedy
		case 1:
			int h = this.state.getHeuristic(heuristicFunction) - o.state.getHeuristic(heuristicFunction);
			return h != 0 ? h : 1;
		// A*
		case 2:
			int f = (this.pathCost + this.state.getHeuristic(heuristicFunction))
					- (o.pathCost + o.state.getHeuristic(heuristicFunction));
			return f != 0 ? f : 1;
		default:
			return 0;
		}
	}

	public static Collection<Node> setSearchStrategy(Collection<Node> nodes, int strategy) {
		for (Node o : nodes) {
			o.searchStrategy = strategy;
		}
		return nodes;
	}

}
