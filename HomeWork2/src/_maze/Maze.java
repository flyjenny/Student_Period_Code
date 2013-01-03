/**
 * Copyright ® 2010 Shanghai Bennychan.
 * 
 */
package _maze;

import java.util.*;

//Type of each Piont.
class Piont{
	int x;		//x-axis
	int y;		//y-axis
	int data;	//data of piont
	int flag=0;	//Flag for judging whether this piont has been visited.
//	Piont before;
	int father;
}

	public class Maze {
		Maze(int N,int M,int C,int sX,int sY,int dX,int dY){
			width=N;
			length=M;
			cash=C;
			startX=sX;
			startY=sY;
			destX=dX;
			destY=dY;
			Queue=new int[N*M][4]; 
			Queue[0][0]=sX;
			Queue[0][1]=sY;
			Queue[0][2]=-1;
			Queue[0][3]=C;
			maze=new Piont[N][M];

			for(int k=0;k<N;k++){
				for(int l=0;l<M;l++){
					maze[k][l]=new Piont();
				}
			}
			for (int i=0;i<N;i++){
				if (i==0||i==(N-1)){
					for (int j=0;j<M;j++){
					//	System.out.print('*');
						maze[i][j].data=-1;
					}
				}
				else for (int m=0;m<M;m++){
					if (m==0||m==(M-1)){
					//	System.out.print('*');
						maze[i][m].data=-1;
					}
					else{
					//	System.out.print('0');
						Random random=new Random();
						maze[i][m].data=random.nextInt(10);
						}
				}
				}
		}


	public void SearchPath(){
		Maze labyrinth=new Maze(width,length,cash,startX,startY,destX,destY);
		int f=0;
		int top=0;
		int x = startX;
		int y = startY;
		int j=0;
		int r=1;
		int i=1;
		while(f<=top){
			i=1;
	//		System.out.println("f "+f);
	//		System.out.println("top:"+top);
			while(i<=4){   //BFS method to Search the shortest path.
	//			System.out.println("data "+labyrinth.maze[x][y].data);
	//			System.out.println("Queue[f][3] "+Queue[f][3]);
				if (i==1&&labyrinth.maze[x][y].data!=-1&&Queue[r][3]>=0){
					x=Queue[f][0];
					y=Queue[f][1]+1;
					Queue[r][3]=Queue[r][3]-labyrinth.maze[x][y].data;
					labyrinth.maze[x][y].flag=1;
				}
				if (i==2&&labyrinth.maze[x][y].data!=-1&&Queue[r][3]>=0){
					x=Queue[f][0]+1;
					y=Queue[f][1];
					Queue[r][3]=Queue[r][3]-labyrinth.maze[x][y].data;
					labyrinth.maze[x][y].flag=1;
				}
				if (i==3&&labyrinth.maze[x][y].data!=-1&&Queue[r][3]>=0){
					x=Queue[f][0];
					y=Queue[f][1]-1;
					Queue[r][3]=Queue[r][3]-labyrinth.maze[x][y].data;
					labyrinth.maze[x][y].flag=1;
				}
			
				if (i==4&&labyrinth.maze[x][y].data!=-1&&Queue[r][3]>=0){
					x=Queue[f][0]-1;
					y=Queue[f][1];
					Queue[r][3]=Queue[r][3]-labyrinth.maze[x][y].data;
					labyrinth.maze[x][y].flag=1;
				}
				
				if(x==destX&&y==destY){
					while(f!=-1){
						System.out.println(Queue[f][0]+","+Queue[f][1]);
						f=Queue[f][2];
					}
					j=1;
					f=top;
				}
				
				
				else{
		//			System.out.println("labyrinth.maze[x][y].flag"+labyrinth.maze[x][y].flag);
		//			System.out.println("labyrinth.maze[x][y].data"+labyrinth.maze[x][y].data);
					
					if(labyrinth.maze[x][y].data!=-1&&Queue[f][3]>=0){
			//			System.out.println("Show me");
						top++;
						r++;
						Queue[top][0]=x;
						Queue[top][1]=y;
						Queue[top][2]=f;
						Queue[top][3]=Queue[r][3];
					}
					i++;
				}
				
				
			}
			f++;
		}
		if(j==0)
			System.out.println("No Path!");
		else 
			System.out.println("被封锁");
	}
		
		private int width;		//The width of maze.
		private int length;		//The length of maze.
		private int cash;		//The cash we get.

		private int startX,startY;//START  coordinate 
		private int destX,destY;	//DESTINATION  coordinate 
		Piont[][] maze;				//MAZE
		int[][] Queue;			//Queue for BFS
	
	
	public static void main(String[]args){
		System.out.println("Input the width of maze (N): ");
		Scanner stdin = new Scanner( System.in );
		int N=stdin.nextInt();
		System.out.println("Input the length of maze (M): ");
		int M=stdin.nextInt();
		System.out.println("Input the Cash : ");
		int cash=stdin.nextInt();
		System.out.println("Input the START X: ");
		int sx=stdin.nextInt();
		System.out.println("Input the START Y: ");
		int sy=stdin.nextInt();
		System.out.println("Input the DESTINATION X: ");
		int dx=stdin.nextInt();
		System.out.println("Input the DESTINATION Y: ");
		int dy=stdin.nextInt();
		Maze m=new Maze(N,M,cash,sx,sy,dx,dy);
		m.SearchPath();
		for (int p=0;p<N;p++){
			for(int q=0;q<M;q++){
				if (m.maze[p][q].data==-1)
					System.out.print('*');
				else
				System.out.print(m.maze[p][q].data);
			}
				System.out.println();
		}
		
		
	}
}
