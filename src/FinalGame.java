import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Stack;

public class FinalGame extends SearchProblem {
	public final static int U = 1;
	public final static int R = 2;
	public final static int D = 3;
	public final static int L = 4;
	public HashSet<Integer> stateIDs;

	public FinalGame() {
		super();
		this.actions = new int[] { U, R, D, L };
		this.stateIDs = new HashSet<Integer>();
	}

	public static String bfs(String str) {
		FinalGame prob = new FinalGame();
		prob.initialState = new Node(new Grid(str), null, 0, 0, 0);
		prob.stateIDs.add(prob.initialState.state.getStateid());
		if (prob.goalTest(prob.initialState.state)) {
			return ";" + "0";
		}
		Object[] searchRes = bfSearch(prob);
		if ((searchRes != null) && (searchRes[0] != null)) {

			Node n = (Node) searchRes[0];
			ArrayList<Integer> a = getActions(n);
			Collections.reverse(a);
			ArrayList<String> actionSeq = new ArrayList<>();
			for (Object object : a) {
				switch ((int) object) {
				case U:
					actionSeq.add("up");
					break;
				case R:
					actionSeq.add("right");
					break;
				case D:
					actionSeq.add("down");
					break;
				case L:
					actionSeq.add("left");
					break;
				default:
					break;
				}
			}
			String path = actionSeq.toString();
			return (path.substring(1, path.length() - 1)).replace(" ", "") + ";" + n.depth;
		} else {
			return "No Solution";
		}
	}

	public static Object[] bfSearch(SearchProblem s) {
		Object[] ret = new Object[2];
		Node bestgoal = null;
		Node n = new Node(s.initialState.state, null, 10, 0, 0);
		LinkedList<Node> nodesQueue = new LinkedList<Node>();
		nodesQueue.add(n);
		int expandedNodes = 0;
		while (!nodesQueue.isEmpty()) {
			for (int i = 0; i < nodesQueue.size(); i++) {
				Node dequeuedNode = nodesQueue.remove();
				if (s.goalTest(dequeuedNode.state)) {
//					
					if (bestgoal == null) {
						bestgoal = dequeuedNode;
					} else {
						if (dequeuedNode.depth < bestgoal.depth) {
							bestgoal = dequeuedNode;
						}
					}
				} else {

					ArrayList<Grid> objectArr = s.aplyActions(dequeuedNode);
					expandedNodes++;
					for (int j = 0; j < objectArr.size(); j++) {
						if (objectArr.get(j) != null) {
							Grid ws = (Grid) objectArr.get(j);
							Node expandedNode = new Node(ws, dequeuedNode, j, dequeuedNode.depth + 1, 0);

							ArrayList<Integer> actions = getActions(expandedNode);
							expandedNode.pathcost = s.pathCost(actions);

							nodesQueue.add(expandedNode);
						}
					}

				}

			}
		}

		ret[0] = bestgoal;
		if (ret[0] != null) {
			System.out.println(((Node) ret[0]).state);

			ret[1] = expandedNodes;
		}

		return ret;
	}

	public static String dfs(String str) {
		FinalGame prob = new FinalGame();
		prob.initialState = new Node(new Grid(str), null, 0, 0, 0);
		prob.stateIDs.add(prob.initialState.state.getStateid());
		if (prob.goalTest(prob.initialState.state)) {
			return ";" + "0";
		}
		Object[] searchRes = bfSearch(prob);
		if ((searchRes != null) && (searchRes[0] != null)) {
			Node n = (Node) searchRes[0];
			ArrayList<Integer> a = getActions(n);
			Collections.reverse(a);
			ArrayList<String> actionSeq = new ArrayList<>();
			for (Object object : a) {
				switch ((int) object) {
				case U:
					actionSeq.add("up");
					break;
				case R:
					actionSeq.add("right");
					break;
				case D:
					actionSeq.add("down");
					break;
				case L:
					actionSeq.add("left");
					break;
				default:
					break;
				}
			}

			String path = actionSeq.toString();
			return (path.substring(1, path.length() - 1)).replace(" ", "") + ";" + n.depth;
		} else {
			return "No Solution";
		}
	}

	public static Object[] dfSearch(SearchProblem s) {
		Object[] ret = new Object[2];
		Node bestgoal = null;
		Node n = s.initialState;
		Stack<Node> q = new Stack<>();
		q.push(n);
		while (!q.isEmpty()) {
			Node node = q.pop();
			if (s.goalTest(node.state)) {
				bestgoal = node;
				ret[0] = bestgoal;
				if (ret[0] != null) {
					ret[1] = bestgoal.depth;
				}

				return ret;

			} else {

				ArrayList<Grid> expantion = s.aplyActions(node);

				for (int i = 0; i < expantion.size(); i++) {
					if ((expantion.get(i) != null)) {
						Node nn = new Node(expantion.get(i), node, i, node.depth + 1, 0);
						ArrayList<Integer> actions = getActions(nn);
						nn.pathcost = s.pathCost(actions);
						q.push(nn);

					}
				}

			}
		}
		return null;

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String grid = "3,3;0,0;0,1,1,1;0,2";
		String grid2 = "10,10;1,6;2,4,5,8,0,8,0,9,9,1,7,2,2,5,2,6,5,9,6,4;4,9";
		String g5 = "15,5;7,1;7,3,9,1,13,2,4,4,7,3,10,1,8,2,3,0,5,4,2,4;2,3";
		System.out.println(new Grid(g5));
		System.out.println(bfs(g5));
	}

	public static ArrayList<Integer> getActions(Node n) {
		ArrayList<Integer> o = new ArrayList<>();
		Node c = n;
		do {
			o.add(c.operator);
			c = c.parentNode;
		} while (c.parentNode != null);
		return o;
	}

	@Override
	public Grid transferFunction(Node node, int action) {
		if (goalTest(node.state)) {
			return null;
		}
		switch (action) {
		case D:
			return node.state.move(D);
		case L:
			return node.state.move(L);
		case R:
			return node.state.move(R);
		case U:
			return node.state.move(U);
		default:
			return null;
		}
	}

	@Override
	public ArrayList<Grid> aplyActions(Node node) {
		ArrayList<Grid> r = new ArrayList<>();
		for (int action : actions) {
			Grid childstate = transferFunction(node, action);
			if ((childstate != null)) {
				if (!stateIDs.contains(childstate.getStateid())) {
					r.add(childstate);
					stateIDs.add(childstate.getStateid());
				} else {
					r.add(null);
				}
			} else {
				r.add(null);
			}
		}
		return r;
	}

	@Override
	public boolean goalTest(Grid state) {
		return state.isGoal();
	}

	@Override
	public int pathCost(ArrayList<Integer> actions) {
		int sum = 0;
		for (Object act : actions) {
			if (act != null) {
				sum++;
			}

		}
		return sum;
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
		int[] p = player.clone();
		switch (dir) {
		case 1:
			if (player[0] == 0) {
				return null;
			} else {
				if (grid[player[0] - 1][player[1]] == null) {
					p[0] = p[0] - 1;
					return new Grid(grid, p, end);
				}
			}
		case 2:
			if (player[1] == maxCol - 1) {
				return null;
			} else {
				if (grid[player[0]][player[1] + 1] == null) {
					p[1] = p[1] + 1;
					return new Grid(grid, p, end);
				}
			}
		case 3:
			if (player[0] == maxRow - 1) {
				return null;
			} else {
				if (grid[player[0] + 1][player[1]] == null) {
					p[0] = p[0] + 1;
					return new Grid(grid, p, end);
				}
			}
		case 4:
			if (player[1] == 0) {
				return null;
			} else {
				if (grid[player[0]][player[1] - 1] == null) {
					p[1] = p[1] - 1;
					return new Grid(grid, p, end);
				}
			}
		default:
			return null;
		}
	}

	public int getStateid() {
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
	int depth;
	int pathcost;

	public Node(Grid state, Node parentNode, int operator, int depth, int pathcost) {
		this.state = state;
		this.parentNode = parentNode;
		this.operator = operator;
		this.depth = depth;
		this.pathcost = pathcost;
	}

}

abstract class SearchProblem {
	Node initialState;
	int[] actions;

	public abstract Grid transferFunction(Node node, int action);

	public abstract int pathCost(ArrayList<Integer> actions);

	public abstract ArrayList<Grid> aplyActions(Node node);

	public abstract boolean goalTest(Grid state);

}