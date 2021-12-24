public class FinalGame {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String grid = "3,3;0,2;0,1,1,1;0,2";
		String grid2= "10,10;1,6;2,4,5,8,0,8,0,9,9,1,7,2,2,5,2,6,5,9,6,4;4,9";
		Grid g =new Grid(grid);
		System.out.println(g);
		System.out.println(g.isGoal());
	}

}
class Grid{
	String [][] grid;
	int[] player;// player[0]= player row ,player[1]= player col
	int[] end;//[row,col]
	public Grid(String gridStr) {
		String [] gsa=gridStr.split(";");
		String [] size =gsa[0].split(",");
		String [] start = gsa[1].split(",");
		String [] finish = gsa[3].split(",");
		String [] obs=gsa[2].split(",");
		this.player= new int[2];
		player[0]=Integer.parseInt(start[0]);
		player[1]=Integer.parseInt(start[1]);
		this.end= new int[2];
		end[0]=Integer.parseInt(finish[0]);
		end[1]=Integer.parseInt(finish[1]);
		this.grid=new String [Integer.parseInt(size[0])] [Integer.parseInt(size[1])];
		for (int i = 0; i < obs.length; i=i+2) {
			int or=Integer.parseInt(obs[i]);
			int oc=Integer.parseInt(obs[i+1]);
			grid[or][oc]=" O";
		}
		
	}
	public int getStateindex() {
		return this.grid[0].length  *this.player[0] + this.player[1];
	}
	public boolean isGoal() {
		return (player[0]==end[0])&&(player[1]==end[1]);
	}
	@Override
	public String toString() {
		String s="";
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				if(i==player[0]&& j==player[1]) {
					if(i==end[0]&&j==end[1]) {
						s+="PE";
					}else {
						
						s+=" P";
					}
				}else {
					if(i==end[0]&&j==end[1]) {
						s+=" E";
					}else {
						
						if(grid[i][j]!=null) {
							s+=grid[i][j];					
						}else {
							s+="__";
						}
					}
				}
				s+="|";
			}
			s+="\n";
		}
		return s;
	}
}
