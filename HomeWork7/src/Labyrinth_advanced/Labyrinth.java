package Labyrinth_advanced;

import javax.swing.SwingUtilities;


class Player_thread extends Thread {
	private int ID = 0;
	Memory Space;
	public Player_thread(int id, Memory m) {
		super();
		ID = id;
		Space = m;
	}
	
	
	//�ж���һ�������Ƿ�������룬���˱߽粻�ɽ��룻ǽ�ڲ��ɽ��룻����ר�����ɽ��룻�ѱ�����ռ�첻�ɽ���
	public boolean accessable(int x, int y, int cash, int trace){
		if ((x < 0)||(x >= Space.SizeX))	{return false;}			//�����Թ�X�߽�
		if ((y < 0)||(y >= Space.SizeY))	{return false;}			//�����Թ�Y�߽�
		if ((Space.Map[x][y] < -9)&&(-Space.Map[x][y]-10 != ID))	{return false;}	//�Թ�����С��-9���Ҳ����ڸ���ר�����Ӳ��������
		if (Space.Map[x][y] > -10)	{cash -= Space.Map[x][y];}		//�����Թ������ϵĻ��Ѹ����ֽ�
		if (cash < 0)	{return false;}								//�����ֽ�	
		int[] temp = new int[6];
		for (int i = 0; i < 3; i++){
			temp[i * 2] = Space.Que[trace][i].X;
			temp[i * 2 + 1] = Space.Que[trace][i].Y;
		}
		temp[ID*2] = x;	temp[ID*2+1] = y;
		while (trace != 0){
			trace = Space.Pre[trace];
			if ((temp[0]==Space.Que[trace][0].X)&&(temp[1]==Space.Que[trace][0].Y)&&
					(temp[2]==Space.Que[trace][1].X)&&(temp[3]==Space.Que[trace][1].Y)&&
					(temp[4]==Space.Que[trace][2].X)&&(temp[5]==Space.Que[trace][2].Y)){
				return false;
			}
		}
		return true;
	}
	public void run() {
		while (!Space.Ready){
			try{
				sleep(100);
			}
			catch(InterruptedException ex){}
		}
		Direction[] DIR = new Direction[4];
		DIR[0] = new Direction(1, 0);	DIR[1] = new Direction(0, 1);
		DIR[2] = new Direction(-1,0);	DIR[3] = new Direction(0,-1);		
		while (!Space.Escaped[ID]) {
			synchronized(this){
			boolean flag = false;
			for (int i = 0; i < 3; i++) {
				if ((!Space.Escaped[i])&&(Space.Front[i] != Space.rear)){
					flag = true;	break;
				}
			}
			if (!flag) {			//�޷��߳��Թ�
				System.out.println("ID: "+ID+" died.");
				break;
			}
			if (Space.Front[ID] == Space.rear){	//û�еִ��յ㣬���ǻ���·�ߣ�����
				try{
					sleep(100);
				}
				catch(InterruptedException ex){}
				continue;
			}
			if (Space.Front[ID] > Space.MAXSIZE){
				System.out.println("Memory out...");
				break;
			}
			else {		//�����յ㣬�߳��Թ�
				int front = Space.Front[ID];
				if (Space.Que[front] == null) {System.out.println("Front="+front);continue;}
				if ((Space.Que[front][ID].X == Space.DestX)&&(Space.Que[front][ID].Y == Space.DestY)) {
					System.out.println("ID: "+ID+" Walk Out");
					Space.Escaped[ID] = true;
					break;
				}
				
				
				//�ÿ�������ķ������ӵ�ǰ��ֱ����ĸ�����̽��ǰ��
				for (int dir = 0; dir < 4; dir++) {
					int TempX = Space.Que[front][ID].X + DIR[dir].X;//���½�Ҫ�ߵ��ĸ���X
					int TempY = Space.Que[front][ID].Y + DIR[dir].Y;//���½�Ҫ�ߵ��ĸ���Y
					int Remains = Space.Que[front][ID].Cash;		//���½�Ҫ�ߵ��ĸ��Ӻ�ʣ�µ��ֽ�
					if (accessable(TempX, TempY, Remains, front)){	//��һ����Խ���
						if (Space.Map[TempX][TempY] > -10)	{Remains -= Space.Map[TempX][TempY];}
						int a, b, c;
						a = Space.Que[front][0].X;	b = Space.Que[front][0].Y;	c = Space.Que[front][0].Cash;
						Space.Que[Space.rear][0] = new Status();
						Space.Que[Space.rear][0].X = a;	Space.Que[Space.rear][0].Y = b;Space.Que[Space.rear][0].Cash = c;
						a = Space.Que[front][1].X;	b = Space.Que[front][1].Y;	c = Space.Que[front][1].Cash;
						Space.Que[Space.rear][1] = new Status();
						Space.Que[Space.rear][1].X = a;	Space.Que[Space.rear][1].Y = b;Space.Que[Space.rear][1].Cash = c;
						a = Space.Que[front][2].X;	b = Space.Que[front][2].Y;	c = Space.Que[front][2].Cash;
						Space.Que[Space.rear][2] = new Status();
						Space.Que[Space.rear][2].X = a;	Space.Que[Space.rear][2].Y = b;Space.Que[Space.rear][2].Cash = c;
						a = TempX;	b = TempY;	c = Remains;
						Space.Que[Space.rear][ID].X = TempX;
						Space.Que[Space.rear][ID].Y = TempY;
						Space.Que[Space.rear][ID].Cash = Remains;
						Space.Pre[Space.rear] = front;
						Space.rear++;
					}
				}
				Space.Front[ID]++;
			}
			try{
				sleep(100);
			}
			catch(InterruptedException e){};
		}
		}
	}
}

//��¼�Թ�����
class Direction {
	int X, Y;
	public Direction(int x, int y) {
		X = x;	Y = y;
	}
}

//��¼��ǰ�����Լ��ֽ�
class Status {
	int X;
	int Y;
	int Cash;
	
}


//��ס�Թ���Ϣ����
class Memory extends Thread{
	int MAXSIZE;		//�Թ���������
	Status[][] Que;		//��¼��ÿ����ÿ�������ϵ�X��Y��Cash
	int[] Pre;
	int rear;			//ָ���β
	int[] Front;		//ָ�����
	boolean[] Escaped;	//���������Ƿ��߳��Թ���Ϣ
	int SizeX, SizeY;		//�Թ���С��Ҫ��SizeX*SizeY<=MAXSIZE
	int[][] Map;			//���ӻ��Թ���С
	int DestX, DestY;		//�յ�����
	int[] IniX, IniY;		//�������˳�ʼ����
	int[] IniCash;			//�������˳�ʼ�ֽ�
	boolean Ready;			//�ж��Ƿ��Ѿ����ú�������Ϣ
	public Memory(){
		MAXSIZE = 1000;
		Que = new Status[MAXSIZE][3];
		Pre = new int[MAXSIZE];
		rear = 1;
		Front = new int[3];
		Escaped = new boolean[3];
		SizeX = 4;	SizeY = 5;
		Map = new int[SizeX][SizeY];
		IniX = new int[3];	IniY = new int[3];	IniCash = new int[3];
		Ready = false;
	}
	public void run(){
		
		for (int i = 0; i < 3; i++){
			while ((Demo.Vertex[0] == -1)||(Demo.Vertex[1] == -1)){
				try{
					sleep(100);
				}
				catch(InterruptedException ex) {}
			}
			IniX[i] = Demo.Vertex[0];	IniY[i] = Demo.Vertex[1];	//����Demo���û�����趨�ó�ʼ����
			System.out.println("Vertex "+i+" OK!");
			Demo.Vertex[0] = -1;	Demo.Vertex[1] = -1;
		}
		while ((Demo.Vertex[0] == -1)||(Demo.Vertex[1] == -1)){
			try{
				sleep(100);
			}
			catch(InterruptedException ex) {}
		}
		DestX = Demo.Vertex[0];	DestY = Demo.Vertex[1];			//�����û�����趨���յ�����
		System.out.println("Destination OK!");
		
		while (!Demo.CashOK) {
			try{
				sleep(100);
			}
			catch(InterruptedException ex){}
		}
		IniCash[0] = Demo.Cash[0];	IniCash[1] = Demo.Cash[1];	IniCash[2] = Demo.Cash[2];//�����û������趨�ó�ʼ�ֽ�
		System.out.println("Cash set: "+IniCash[0]+","+IniCash[1]+","+IniCash[2]);
		//-----------------------------------------
		while (!Demo.MazeOK) {		//�Թ��Ƿ��������
			try{
				sleep(100);
			}
			catch(InterruptedException e){}
		}
		System.out.println("Labyrinth OK!");
		for (int i = 0; i < SizeX; i++){
			for (int j = 0; j < SizeY; j++) {
				Map[i][j] = Demo.Maze[i][j];
				System.out.print(Map[i][j]+" ");
			}
			System.out.println();
		}
		
		//���������ʼ������
		Que[0][0] = new Status();	Que[0][0].X = IniX[0];	Que[0][0].Y = IniY[0];	Que[0][0].Cash = IniCash[0];
		Que[0][1] = new Status();	Que[0][1].X = IniX[1];	Que[0][1].Y = IniY[1];	Que[0][1].Cash = IniCash[1];
		Que[0][2] = new Status();	Que[0][2].X = IniX[2];	Que[0][2].Y = IniY[2];	Que[0][2].Cash = IniCash[2];
		Pre[0] = -1;

		Ready = true;
	}
}
public class Labyrinth {
	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Demo inst = new Demo();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);			//��ʾDemo
				Memory Handle = new Memory();
				Handle.start();					//���߳̿�ʼ
				Player_thread[] Demo = new Player_thread[3];
				for (int i = 0; i < 3; i++) {
					Demo[i] = new Player_thread(i, Handle);
					Demo[i].start();
				}
			}
		});
		//Thread.sleep(10000);
	}
}
