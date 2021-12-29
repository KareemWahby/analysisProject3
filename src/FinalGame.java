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
			for (int i : a) {
				switch (i) {
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
							Node expandedNode = new Node(ws, dequeuedNode, j+1, dequeuedNode.depth + 1, 0);

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
		Object[] searchRes = dfSearch(prob);
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
						Node nn = new Node(expantion.get(i), node, i+1, node.depth + 1, 0);
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
		 
		String g1 = "4,5;3,2;0,2,1,3,1,0,3,3,2,0;1,4";
		String g2 = "5,5;2,0;0,3,3,0,3,2,3,0,2,2,1,2,2,4,3,2,4,4,4,4;2,0";
		String g3 = "6,6;0,0;2,5,1,1,1,0,5,0,5,2,4,1,1,4,1,1,4,5,0,5,1,2,0,1,5,5,0,1,3,4,1,1,2,3,5,0,1,1,4,4;5,4";
		String g4 = "10,10;5,3;0,1,8,0,3,9,5,8,6,7,2,1,0,8,3,2,0,0,7,4,3,9,5,5,1,0,2,7,2,0;1,7";
		String g5 = "15,5;7,1;7,3,9,1,13,2,4,4,7,3,10,1,8,2,3,0,5,4,2,4;2,3";
		String g6 = "10,10;4,4;9,1,1,6,8,5,1,1,4,0,1,2,1,5,9,0,2,1,7,3,9,6,5,2,9,0,6,7,4,5,2,5,7,3,8,9,8,2,6,0,2,7,0,5,9,1,8,1,6,6,0,0,4,3,9,0,8,1,5,4,7,6,2,1,3,9,5,7,2,1,8,2,5,5,3,4,6,7,7,7;3,2";
		String g7 = "25,25;15,4;21,20,16,14,4,15,7,1,8,4,11,4,13,1,12,0,16,4,11,19,11,1,16,16,5,18,20,22,0,15,11,4,19,10,5,11,14,9,22,3,17,24,21,13,4,24,15,22,8,12,0,2,19,7,3,0,2,16,21,8,6,22,10,23,15,23,21,15,19,10,20,9,6,17,24,6,7,4,19,24,22,0,2,0,10,5,2,20,19,11,4,2,24,10,19,15,14,4,2,10;23,18";
		String g8 = "25,25;18,21;0,6,24,21,23,11,13,18,6,5,6,5,1,4,23,14,7,0,12,15,5,14,5,10,1,0,7,11,16,24,15,21,19,0,13,17,3,1,8,11,9,22,1,7,3,13,12,6,22,6,15,0,13,10,11,3,14,22,5,22,17,23,1,0,12,23,22,5,13,1,9,6,14,9,5,2,19,12,6,2,4,2,14,22,16,13,14,2,3,17,18,12,7,24,9,10,16,18,20,5;14,1";
		String g9 = "100,100;86,39;50,77,87,36,72,74,14,44,99,1,90,5,49,4,14,28,2,30,31,32,39,73,48,91,14,60,51,44,51,40,92,6,87,59,11,84,64,9,48,2,53,10,90,22,20,59,24,54,89,81,11,46,52,93,83,39,59,74,77,86,67,70,42,42,4,0,28,36,54,83,66,68,40,85,43,64,90,87,66,82,56,6,34,38,72,1,90,4,52,1,23,4,93,48,79,16,47,18,96,90,24,79,29,58,39,81,64,27,0,16,70,31,10,4,92,13,6,14,54,29,2,67,61,50,97,99,26,56,5,40,83,39,26,69,4,17,36,27,76,19,51,39,7,83,3,52,80,67,32,70,84,69,78,15,68,14,67,29,64,70,21,96,66,25,0,48,10,48,37,78,14,66,21,96,41,70,50,16,13,55,30,79,78,37,33,82,43,44,32,15,12,42,23,47,54,11,1,76,94,63,10,96,53,50,8,83,13,83,81,88,53,76,24,75,9,65,63,85,49,91,25,28,10,52,82,30,29,85,64,64,30,65,43,59,59,57,94,82,47,5,3,46,73,84,56,16,34,98,73,40,74,74,53,78,43,27,90,36,23,79,88,66,46,44,36,83,66,63,40,23,74,73,97,40,37,30,85,89,35,9,85,6,26,77,6,86,83,8,73,2,11,27,49,16,68,15,15,84,81,40,99,66,96,59,41,3,8,21,9,58,79,39,61,50,41,67,87,77,56,53,20,42,30,32,29,70,0,62,66,54,9,87,60,6,61,83,48,89,88,9,13,19,5,62,56,38,31,39,62,99,33,53,40,88,31,72,82,90,18,21,2,66,7,85,11,9,72,47,43,31,68,4,41,98,34,78,88,59,41,82,42,74,54,82,43,71,46,23,17,41,99,85,53,4,59,79,37,68,14,90,53,28,19,10,94,58,9,26,48,58,47,74,86,65,66,70,53,14,84,9,9,21,68,27,73,20,81,88,29,87,12,79,26,59,96,88,54,42,56,29,19,11,64,4,30,68,92,96,32,57,55,76,34,9,71,57,86,11,50,29,32,89,12,15,79,13,75,46,30,36,68,53,26,13,89,11,42,40,25,16,42,8,14,94,27,45,77,99,43,25,49,80,4,49,85,67,67,58,7,49,33,59,20,6,21,96,65,55,98,39,48,2,27,11,34,83,71,31,2,95,15,89,56,60,18,50,58,32,73,18,8,56,69,77,5,45,71,72,74,13,90,94,72,23,19,51,98,59,6,55,56,32,3,67,33,30,68,69,70,20,34,92,62,87,69,40,95,25,96,2,48,93,54,5,8,35,69,74,26,14,63,47,20,95,78,37,66,33,16,81,53,66,82,86,60,3,30,50,29,59;30,85";
		String g10 = "100,100;18,76;98,15,2,57,68,79,30,17,45,71,60,79,69,93,38,53,21,68,45,22,46,21,89,67,27,62,86,54,35,89,34,67,84,38,43,42,19,72,42,6,83,9,39,82,1,70,80,68,45,35,84,92,67,5,63,51,65,31,18,85,42,49,82,43,45,87,27,13,23,0,97,38,21,42,11,53,44,60,86,25,52,62,5,88,26,90,47,58,26,18,90,82,94,44,20,96,94,82,31,64,76,99,76,45,39,64,41,81,14,51,68,73,45,68,57,58,49,26,70,63,23,47,64,99,67,27,31,16,99,69,26,9,12,34,18,50,88,38,92,13,2,8,37,18,91,28,84,19,7,76,49,68,1,15,1,34,76,97,24,19,8,32,84,39,24,30,88,2,17,69,30,80,7,64,73,11,50,95,47,37,50,72,68,62,76,78,88,62,15,6,22,51,85,29,15,16,11,45,62,46,24,88,74,38,87,47,19,76,39,46,67,20,71,89,73,95,97,52,87,86,27,93,65,25,90,51,22,81,50,52,37,13,31,77,51,35,39,23,94,56,5,60,1,52,85,53,96,81,35,42,17,22,89,41,39,80,66,13,86,92,0,54,70,34,87,6,95,8,3,63,41,83,42,13,32,33,42,60,8,29,96,0,54,8,37,62,36,66,60,58,75,89,14,29,39,81,90,4,56,98,91,45,92,75,15,2,96,62,66,30,92,91,62,3,58,28,99,1,28,51,72,0,78,81,97,88,29,35,43,46,42,1,43,68,65,62,22,95,24,36,10,87,38,49,48,59,26,75,66,73,79,60,19,81,64,55,73,65,44,75,26,78,86,27,1,0,77,26,74,5,18,69,42,56,74,7,97,34,71,29,86,20,73,31,33,57,62,19,83,34,46,92,97,53,30,1,17,52,91,82,4,86,40,51,58,95,12,31,93,10,22,66,74,92,67,99,78,10,76,60,12,46,16,36,21,0,46,81,83,66,80,66,94,57,41,16,17,39,20,92,93,63,53,84,59,55,76,9,98,86,14,36,30,94,37,71,17,18,74,0,81,41,27,61,42,52,1,46,97,84,27,50,97,67,35,67,47,27,77,47,84,51,48,84,71,2,11,78,37,91,86,33,64,77,34,47,32,95,5,94,85,87,42,56,33,26,14,59,14,32,37,0,71,82,96,60,29,39,93,62,66,32,76,97,61,45,73,68,51,93,34,35,86,88,6,81,74,29,11,76,25,87,73,44,55,76,36,3,76,56,54,95,44,47,75,64,5,83,50,30,17,66,92,42,55,67,67,67,70,27,53,34,70,7,28,47,37,87,33,20,19,56,67,84,25,34,94,7,87,15,99,24,34,91,47,85,64,20,75,5;74,13";
		
		Grid g =new Grid(g1);
		System.out.println(new Grid(g1));

		System.out.println(g.move(U).move(R).move(U));
		//System.out.println(bfs(g1));
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
				}else {
					return null;
				}
			}
		case 2:
			if (player[1] == maxCol - 1) {
				return null;
			} else {
				if (grid[player[0]][player[1] + 1] == null) {
					p[1] = p[1] + 1;
					return new Grid(grid, p, end);
				}else {
					return null;
				}
			}
		case 3:
			if (player[0] == maxRow - 1) {
				return null;
			} else {
				if (grid[player[0] + 1][player[1]] == null) {
					p[0] = p[0] + 1;
					return new Grid(grid, p, end);
				}else {
					return null;
				}
			}
		case 4:
			if (player[1] == 0) {
				return null;
			} else {
				if (grid[player[0]][player[1] - 1] == null) {
					p[1] = p[1] - 1;
					return new Grid(grid, p, end);
				}else {
					return null;
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