import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Stack;

public class FinalGame{
	public final static int U = 1;
	public final static int R = 2;
	public final static int D = 3;
	public final static int L = 4;



	public static String bfs (String str) {

		return null;
	}
	public static String dfs(String str) {

		return null;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String grid = "3,3;0,0;0,1,1,1;0,2";
		String grid2 = "10,10;1,6;2,4,5,8,0,8,0,9,9,1,7,2,2,5,2,6,5,9,6,4;4,9";
		Grid g= new Grid(grid);
		System.out.println();
	}

	public static Grid transferFunction(Grid state, int action) {
		switch (action) {
		case D:return state.move(D);
		case L:return state.move(L);
		case R:return state.move(R);
		case U:return state.move(U);
		default:return null;
		}
	}

	public static ArrayList<Grid> aplyActions(int[] actions,Grid state) {
		ArrayList<Grid> r=new ArrayList<>();
		for (int action : actions) {
			r.add(transferFunction(state, action));
		}
		return r;
	}

}

class Grid {
	String[][] grid;
	int[] player;// player[0]= player row ,player[1]= player col
	int[] end;// [row,col]

	public Grid(String gridStr) {
		String[] gsa = gridStr.split(";");
		String[] size = gsa[0].split(",");
		String[] start = gsa[1].split(",");
		String[] finish = gsa[3].split(",");
		String[] obs = gsa[2].split(",");
		this.player = new int[2];
		player[0] = Integer.parseInt(start[0]);
		player[1] = Integer.parseInt(start[1]);
		this.end = new int[2];
		end[0] = Integer.parseInt(finish[0]);
		end[1] = Integer.parseInt(finish[1]);
		this.grid = new String[Integer.parseInt(size[0])][Integer.parseInt(size[1])];
		for (int i = 0; i < obs.length; i = i + 2) {
			int or = Integer.parseInt(obs[i]);
			int oc = Integer.parseInt(obs[i + 1]);
			grid[or][oc] = " O";
		}

	}

	public Grid(String[][] grid, int[] player, int[] end) {
		super();
		this.grid = grid;
		this.player = player;
		this.end = end;
	}

	public Grid move(int dir) {// dir values (1=N, 2=E, 3=S, 4=W) returns a state resulting from applying the
		// move action in a specified direction or NULL if the action is invalid
		int maxRow = grid.length;
		int maxCol = grid[0].length;
		int[] p = player;
		switch (dir) {
		case 1:
			if (player[0] == 0) {
				return null;
			} else {
				if (grid[player[0] - 1][player[1]] == null) {
					p[0]=p[0]-1;
					return new Grid(grid, p, end);
				}
			}
		case 2:
			if (player[1] == maxCol - 1) {
				return null;
			} else {
				if (grid[player[0]][player[1]+1] == null) {
					p[1]=p[1]+1;
					return new Grid(grid, p, end);
				}
			}
		case 3:
			if (player[0] == maxRow - 1) {
				return null;
			} else {
				if (grid[player[0] + 1][player[1]] == null) {
					p[0]=p[0]+1;
					return new Grid(grid, p, end);
				}
			}
		case 4:
			if (player[1] == 0) {
				return null;
			} else {
				if (grid[player[0]][player[1]-1] == null) {
					p[1]=p[1]-1;
					return new Grid(grid, p, end);
				}
			}
		default:
			return null;
		}
	}

	public int getStateindex() {
		return this.grid[0].length * this.player[0] + this.player[1];
	}

	public boolean isGoal() {
		return (player[0] == end[0]) && (player[1] == end[1]);
	}

	@Override
	public String toString() {
		String s = "";
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				if (i == player[0] && j == player[1]) {
					if (i == end[0] && j == end[1]) {
						s += "PE";
					} else {

						s += " P";
					}
				} else {
					if (i == end[0] && j == end[1]) {
						s += " E";
					} else {

						if (grid[i][j] != null) {
							s += grid[i][j];
						} else {
							s += "__";
						}
					}
				}
				s += "|";
			}
			s += "\n";
		}
		return s;
	}
}

class Node {
	Grid state;
	Node parentNode;
	int operator;
	public Node(Grid state, Node parentNode, int operator) {
		super();
		this.state = state;
		this.parentNode = parentNode;
		this.operator = operator;
	}

}
