package project.save_westeros;

public class State {
	private int agentRow, agentColumn, numDragonGlass;
	// The actual cells of the grid (ie. map);
	private Cell[][] map;

	public State(int row, int column, int numDragonGlass, Cell[][] map) {
		this.agentRow = row;
		this.agentColumn = column;
		this.numDragonGlass = numDragonGlass;
		this.map = map;
	}

	public int getAgentRow() {
		return agentRow;
	}

	public int getAgentColumn() {
		return agentColumn;
	}

	public void setAgentRow(int agentRow) {
		this.agentRow = agentRow;
	}

	public void setAgentColumn(int agentColumn) {
		this.agentColumn = agentColumn;
	}

	public int getNumDragonGlass() {
		return numDragonGlass;
	}

	public void setNumDragonGlass(int numDragonGlass) {
		this.numDragonGlass = numDragonGlass;
	}

	public Cell[][] getMap() {
		return map;
	}

	public void setMap(Cell[][] map) {
		this.map = map;
	}

	public void setCellAtIndex(int row, int column, Cell cell) {
		this.map[row][column] = cell;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		State state = (State) o;
		return agentRow == state.getAgentRow() && agentColumn == state.getAgentColumn()
				&& numDragonGlass == state.getNumDragonGlass()
				&& !(map[agentRow][agentColumn] instanceof DragonstoneCell);
	}

	@Override
	public String toString() {
		String toString = "";
		toString += "=========\n";
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				if (i == agentRow && j == agentColumn) {
					toString += "[x]"; // place an [x] in place of the player
				} else {
					toString += map[i][j];
				}
			}
			toString += "\n";
		}
		toString += String.format("Dragon Glass = %s", numDragonGlass);
		toString += "\n=========";
		return toString.substring(0, toString.length() - 1);
	}

	public int getHeuristic(int function) {
		int h = 0;
		switch (function) {
		case 1:
			// heuristic = sum of distance between all white walkers + distance between
			// agent and first white walker
			int current_ww_row = agentRow;
			int current_ww_column = agentColumn;
			for (int i = 0; i < map.length; i++) {
				for (int j = 0; j < map[i].length; j++) {
					if (map[i][j] instanceof WhiteWalkerCell) {
						h = h + Math.abs(i - current_ww_row) + Math.abs(j - current_ww_column);
						current_ww_row = i;
						current_ww_column = j;
					}
				}
			}
			break;
		case 2:
			// heuristic = distance(my position, estimated closest white walker) + points
			// left out
			int ww_count = 0;
			for (int i = 0; i < map.length; i++) {
				for (int j = 0; j < map[i].length; j++) {
					if (map[i][j] instanceof WhiteWalkerCell) {
						ww_count++;
						int dist = Math.abs(i - agentRow) + Math.abs(j - agentColumn);
						if (dist < h) {
							h = dist;
						}
					}
				}
			}
			h += (ww_count - 1);
			break;
		}
		return h;
	}

}
