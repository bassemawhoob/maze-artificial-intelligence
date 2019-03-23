package project.save_westeros;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import project.abstracts.SearchProblem;

public class SaveWesteros implements SearchProblem {
	// The dimensions of the grid {m = rows, n = columns}
	private int m, n;
	// The range of random values m,n can take -> {min, max}
	private final int[] range = new int[] { 4, 7 };
	// The initial position of Jon Snow (m, n)
	private State initial_state;
	// The number of  shards given every time Jon reaches the dragonstone randomly between {1,4} inclusive
	private final int NUM_DRAGON_GLASS = 1 + (int) (Math.random() * ((5 - 1) + 1));

	public SaveWesteros() {
		m = range[0] + (int) (Math.random() * ((range[1] - range[0]) + 1));
		n = range[0] + (int) (Math.random() * ((range[1] - range[0]) + 1));
		initial_state = new State(m - 1, n - 1, 0, new Cell[m][n]);
	}

	public void GenGrid() {
		// Loop until an empty cell is found for the Dragonstone
		Cell[][] map = initial_state.getMap();

		for (int i = 1; i > 0;) {
			// Generate a random row and column for the Dragonstone (alternative
			// approach than the
			// one used in the constructor)
			int row = new Random().nextInt(m);
			int column = new Random().nextInt(n);

			// only place the Dragonstone if the cell is free
			if (map[row][column] == null
					&& (row != initial_state.getAgentRow() || column != initial_state.getAgentColumn())) {
				map[row][column] = new DragonstoneCell();
				i--;
			}
		}

		// Generate a random number of White Walkers between 1 & 4 inclusive
		int num_white_walkers = 1 + (int) (Math.random() * ((5 - 1) + 1));
		for (int i = num_white_walkers; i > 0;) {
			// generate a random row and column
			int row = new Random().nextInt(m);
			int column = new Random().nextInt(n);

			// only place the white walker if the cell is free
			if (map[row][column] == null
					&& (row != initial_state.getAgentRow() || column != initial_state.getAgentColumn())) {
				map[row][column] = new WhiteWalkerCell();
				i--;
			}
		}

		// Generate a random number of obstacles between 1 & 4
		int num_obstacles = 1 + (int) (Math.random() * ((5 - 1) + 1));
		for (int i = num_obstacles; i > 0;) {
			// generate a random row and column
			int row = new Random().nextInt(m);
			int column = new Random().nextInt(n);

			// only place the obstacle if the cell is free
			if (map[row][column] == null
					&& (row != initial_state.getAgentRow() || column != initial_state.getAgentColumn())) {
				map[row][column] = new ObstacleCell();
				i--;
			}
		}

		// Initialize the rest of the map with empty cells
		for (int row = 0; row < map.length; row++) {
			for (int column = 0; column < map[row].length; column++) {
				if (map[row][column] == null) {
					map[row][column] = new EmptyCell();
				}
			}
		}

	}

	@Override
	public State getInitialState() {
		return initial_state;
	}

	@Override
	public boolean isGoal(State state) {
		Cell[][] map = state.getMap();
		boolean goalReached = true;
		for (int row = 0; row < m; row++) {
			for (int column = 0; column < n; column++) {
				if (map[row][column] instanceof WhiteWalkerCell) {
					goalReached = false;
				}
			}
		}
		return goalReached;
	}

	@Override
	public List<Operator> getOperators(State state) {
		ArrayList<Operator> operators = new ArrayList<Operator>();
		int agentRow = state.getAgentRow();
		int agentColumn = state.getAgentColumn();
		Cell[][] map = state.getMap();
		if (agentRow > 0 && ((map[agentRow - 1][agentColumn] instanceof EmptyCell)
				|| (map[agentRow - 1][agentColumn] instanceof DragonstoneCell))) {
			operators.add(Operator.UP);
		}
		if (agentRow < m - 1 && ((map[agentRow + 1][agentColumn] instanceof EmptyCell)
				|| (map[agentRow + 1][agentColumn] instanceof DragonstoneCell))) {
			operators.add(Operator.DOWN);
		}
		if (agentColumn > 0 && ((map[agentRow][agentColumn - 1] instanceof EmptyCell)
				|| (map[agentRow][agentColumn - 1] instanceof DragonstoneCell))) {
			operators.add(Operator.LEFT);
		}
		if (agentColumn < n - 1 && ((map[agentRow][agentColumn + 1] instanceof EmptyCell)
				|| (map[agentRow][agentColumn + 1] instanceof DragonstoneCell))) {
			operators.add(Operator.RIGHT);
		}

		if ((agentRow - 1 >= 0 && map[agentRow - 1][agentColumn] instanceof WhiteWalkerCell)
				|| (agentRow + 1 < m && map[agentRow + 1][agentColumn] instanceof WhiteWalkerCell)
				|| (agentColumn - 1 >= 0 && map[agentRow][agentColumn - 1] instanceof WhiteWalkerCell)
				|| (agentColumn + 1 < n && map[agentRow][agentColumn + 1] instanceof WhiteWalkerCell)) {
			operators.add(Operator.KILL);
		}

		return operators;
	}

	@Override
	public State getNextState(State state, Operator operator) {
		int agentRow = state.getAgentRow();
		int agentColumn = state.getAgentColumn();
		int num_dragon_glass = state.getNumDragonGlass();
		Cell[][] map = cloneMap(state.getMap());
		State next_state = new State(agentRow, agentColumn, num_dragon_glass, map);
		switch (operator) {
		case UP:
			next_state.setAgentRow(agentRow - 1);
			next_state.setAgentColumn(agentColumn);
			if (map[next_state.getAgentRow()][next_state.getAgentColumn()] instanceof DragonstoneCell) {
				next_state.setNumDragonGlass(NUM_DRAGON_GLASS);
			}
			break;
		case DOWN:
			next_state.setAgentRow(agentRow + 1);
			next_state.setAgentColumn(agentColumn);
			if (map[next_state.getAgentRow()][next_state.getAgentColumn()] instanceof DragonstoneCell) {
				next_state.setNumDragonGlass(NUM_DRAGON_GLASS);
			}
			break;
		case LEFT:
			next_state.setAgentRow(agentRow);
			next_state.setAgentColumn(agentColumn - 1);
			if (map[next_state.getAgentRow()][next_state.getAgentColumn()] instanceof DragonstoneCell) {
				next_state.setNumDragonGlass(NUM_DRAGON_GLASS);
			}
			break;
		case RIGHT:
			next_state.setAgentRow(agentRow);
			next_state.setAgentColumn(agentColumn + 1);
			if (map[next_state.getAgentRow()][next_state.getAgentColumn()] instanceof DragonstoneCell) {
				next_state.setNumDragonGlass(NUM_DRAGON_GLASS);
			}
			break;
		case KILL:
			if (num_dragon_glass > 0) {
				if (agentRow - 1 >= 0 && map[agentRow - 1][agentColumn] instanceof WhiteWalkerCell) {
					map[agentRow - 1][agentColumn] = new EmptyCell();
				}
				if (agentRow + 1 < m && map[agentRow + 1][agentColumn] instanceof WhiteWalkerCell) {
					map[agentRow + 1][agentColumn] = new EmptyCell();
				}
				if (agentColumn - 1 >= 0 && map[agentRow][agentColumn - 1] instanceof WhiteWalkerCell) {
					map[agentRow][agentColumn - 1] = new EmptyCell();
				}
				if (agentColumn + 1 < n && map[agentRow][agentColumn + 1] instanceof WhiteWalkerCell) {
					map[agentRow][agentColumn + 1] = new EmptyCell();
				}
				next_state.setNumDragonGlass(num_dragon_glass - 1);
			}
		default:
			break;
		}
		return next_state;
	}

	public Cell[][] cloneMap(Cell[][] map) {
		Cell[][] clone = new Cell[m][n];
		for (int i = 0; i < map.length; i++) {
			clone[i] = map[i].clone();
		}
		return clone;
	}

	@Override
	public int getPathCost(State start_state, Operator operator, State dest_state) {
		return 1;
	}

}
