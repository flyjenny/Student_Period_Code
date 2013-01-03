
public class Intersection {
	private int h_num,v_num,total,inter_num=0;
	private line[] lines;
	private int[][] result;
	public Intersection(line[] l,int h,int v,int t){
		lines=l;
		h_num=h;
		v_num=v;
		total=t;
	}
	public int[][] run(){
		int[] h_line=new int[h_num];
		int hn=0,i;
		int k=0,amount,h_head=0;
		result=new int[h_num*v_num+1][2];
		while (k<total){
			amount=1;
			while (k+amount<total&&lines[k].x1==lines[k+amount].x1) amount++;
			int count=amount;
			for (int j=0;j<amount && count>0;j++)
				if (lines[k+j].x1<lines[k+j].x2){
					h_line[hn++]=k+j;
					count--;
				}
			for (int j=0;j<amount && count>0;j++)
				if (lines[k+j].x1==lines[k+j].x2) {
					for (i=h_head;i<hn;i++)
						if (h_line[i]!=-1){
							int y=lines[h_line[i]].y1;
							if ((lines[k+j].y1<=y) && (y<=lines[k+j].y2)){
								inter_num++;
								result[inter_num][0]=lines[k+j].x1;
								result[inter_num][1]=y;
								//System.out.println(inter_num+": "+lines[k+j].x1+"\t"+y);
							}
						}
					count--;
				}
			for (int j=0;j<amount && count>0;j++)
				if (lines[k+j].x1>lines[k+j].x2){
					for (i=h_head;i<hn;i++)
						if (h_line[i]!=-1 && lines[h_line[i]].form==lines[k+j].form) break;
					h_line[i]=-1;
					while (h_head<h_num&&h_line[h_head]==-1) h_head++;
					count--;
				}
			k+=amount;
			/*
			if (lines[k].x1>lines[k].x2){
				for (i=0;i<hn;i++)
					if (h_line[i]!=-1 && lines[h_line[i]].form==lines[k].form) break;
				h_line[i]=-1;
			}else if (lines[k].x1<lines[k].x2){
				h_line[hn++]=k;
			}else if (lines[k].x1==lines[k].x2){
				for (i=0;i<hn;i++){
					if (h_line[i]!=-1){
						int y=lines[h_line[i]].y1;
						if ((lines[k].y1<=y) && (y<=lines[k].y2)){
							System.out.println(lines[k].x1+" "+y);
						}
					}
				}
			}
			*/
			//k++;
		}
		result[0][0]=inter_num;
		return result;
	}
}
