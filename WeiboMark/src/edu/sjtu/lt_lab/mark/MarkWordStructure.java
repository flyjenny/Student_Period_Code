package edu.sjtu.lt_lab.mark;

import java.io.IOException;

/**
 * @author BennyChan
 * 根据总结出来的词间特征结构，标注词间特征
 */
public class MarkWordStructure {
	public static void main(String[] args) throws IOException{
		String readPath = "weibo.txt";
		ReadAndWriteWeibo weibo = new ReadAndWriteWeibo(readPath, "", 0);
		MarkWordStructure mws=new MarkWordStructure(weibo);
		weibo.ReadNext();
		mws.markWordStructure();
		weibo.print();
	}
	
	private ReadAndWriteWeibo $weibo = null;

	public MarkWordStructure(ReadAndWriteWeibo weibo) {
		$weibo = weibo;
	}

	/**
	 * 标记词间的结构
	 */
	public void markWordStructure_old() {
		String morpheme = "0";
		int wordCount = $weibo.getWordCount();
		int startWord = 0;
		for (int j = 0; j < wordCount; j++) {
			morpheme = "0";
			startWord = j;
			if ((j + 1) < wordCount && $weibo.getPos()[j].equals("a")
					&& $weibo.getPos()[j + 1].equals("a")) {
				morpheme = "a_a";
				// System.out.println("a_a");
				// System.out.println("start word:" + j);
				// System.out.println("word count:2");
				j += 1;
			}
			if ($weibo.getPos()[j].equals("d")) {
				if ((j + 2) < $weibo.getPos().length
						&& $weibo.getPos()[j + 1].equals("a")
						&& $weibo.getPos()[j + 2].equals("y")) {
					morpheme = "d_a_y";
					// System.out.println("d_a_y");
					// System.out.println("start word:" + j);
					// System.out.println("word count:3");
					j += 2;
				}
				if ((j + 1) < $weibo.getPos().length
						&& $weibo.getPos()[j + 1].equals("an")) {
					morpheme = "d_an";
					// System.out.println("d_an");
					// System.out.println("start word:" + j);
					// System.out.println("word count:2");
					j += 1;
				}
				if ((j + 1) < $weibo.getPos().length
						&& $weibo.getPos()[j + 1].equals("v")) {
					morpheme = "d_v";
					// System.out.println("d_v");
					// System.out.println("start word:" + j);
					// System.out.println("word count:2");
					j += 1;
				}
			}
			if ($weibo.getPos()[j].equals("n")
					|| $weibo.getPos()[j].equals("tar")) {
				if ((j + 1) < $weibo.getPos().length
						&& $weibo.getPos()[j + 1].equals("v")) {
					morpheme = "n_v";
					// System.out.println("n_v");
					// System.out.println("start word:" + j);
					// System.out.println("word count:2");
					j += 1;
				}
				if ((j + 1) < $weibo.getPos().length
						&& $weibo.getPos()[j + 1].equals("vi")) {
					morpheme = "n_vi";
					// System.out.println("n_vi");
					// System.out.println("start word:" + j);
					// System.out.println("word count:2");
					j += 1;
				}
			}
			if ($weibo.getPos()[j].equals("v")) {
				if ((j + 1) < $weibo.getPos().length
						&& $weibo.getPos()[j + 1].equals("a")) {
					morpheme = "v_a";
					// System.out.println("v_a");
					// System.out.println("start word:" + j);
					// System.out.println("word count:2");
					j += 1;
				}
				if ((j + 1) < $weibo.getPos().length
						&& ($weibo.getPos()[j + 1].equals("n") || $weibo
								.getPos()[j + 1].equals("tar"))) {
					morpheme = "v_n";
					// System.out.println("v_n");
					// System.out.println("start word:" + j);
					// System.out.println("word count:2");
					j += 1;
				}
				if ((j + 1) < $weibo.getPos().length
						&& $weibo.getPos()[j + 1].equals("y")) {
					morpheme = "v_y";
					// System.out.println("v_y");
					// System.out.println("start word:" + j);
					// System.out.println("word count:2");
					j += 1;
				}
				if ((j + 2) < $weibo.getPos().length
						&& $weibo.getPos()[j + 1].equals("d")) {
					if ($weibo.getPos()[j + 2].equals("vf")) {
						morpheme = "v_d_vf";
						// System.out.println("v_d_vf");
						// System.out.println("start word:" + j);
						// System.out.println("word count:3");
						j += 2;
					} else if ($weibo.getPos()[j + 2].equals("y")) {
						morpheme = "v_d_y";
						// System.out.println("v_d_y");
						// System.out.println("start word:" + j);
						// System.out.println("word count:3");
						j += 2;
					}
				}
				if ((j + 3) < $weibo.getPos().length
						&& $weibo.getPos()[j + 1].equals("u")
						&& $weibo.getPos()[j + 2].equals("d")
						&& $weibo.getPos()[j + 2].equals("a")) {
					morpheme = "v_u_d_a";
					// System.out.println("v_u_d_a");
					// System.out.println("start word:" + j);
					// System.out.println("word count:4");
					j += 3;
				}
			}
			if (j != startWord) {
				for (int k = startWord; k <= j; k++) {
					$weibo.getWordStructure()[k] = morpheme;
				}
			}
		}
	}
	public void markWordStructure(){
		int wordCount=$weibo.getWordCount();
		String pos="";
		String regex="([a-zA-z0-9]+,)";
		for(int i=0;i<wordCount;i++){
			pos+=$weibo.getPos()[i]+",";
		}
		for(int i=0;i<wordCount;i++){
			$weibo.getWordStructure()[i]="0";
		}
		int position=0;
		while(pos.length()>0){
			if(pos.startsWith("a,a,")){
				$weibo.getWordStructure()[position]="a_a";
				$weibo.getWordStructure()[position+1]="a_a";
				pos=pos.replaceFirst(regex, "");
				pos=pos.replaceFirst(regex, "");
				position+=2;
			}else if(pos.startsWith("a,n,")){
				$weibo.getWordStructure()[position]="a_n";
				$weibo.getWordStructure()[position+1]="a_n";
				pos=pos.replaceFirst(regex, "");
				pos=pos.replaceFirst(regex, "");
				position+=2;
			}else if(pos.startsWith("d,a,y,")){
				$weibo.getWordStructure()[position]="d_a_y";
				$weibo.getWordStructure()[position+1]="d_a_y";
				$weibo.getWordStructure()[position+2]="d_a_y";
				pos=pos.replaceFirst(regex, "");
				pos=pos.replaceFirst(regex, "");
				pos=pos.replaceFirst(regex, "");
				position+=3;
			}else if(pos.startsWith("d,a,")){
				$weibo.getWordStructure()[position]="d_a";
				$weibo.getWordStructure()[position+1]="d_a";
				pos=pos.replaceFirst(regex, "");
				pos=pos.replaceFirst(regex, "");
				position+=2;
			}else if(pos.startsWith("d,an,")){
				$weibo.getWordStructure()[position]="d_an";
				$weibo.getWordStructure()[position+1]="d_an";
				pos=pos.replaceFirst(regex, "");
				pos=pos.replaceFirst(regex, "");
				position+=2;
			}else if(pos.startsWith("d,v,y,")){
				$weibo.getWordStructure()[position]="d_v_y";
				$weibo.getWordStructure()[position+1]="d_v_y";
				$weibo.getWordStructure()[position+2]="d_v_y";
				pos=pos.replaceFirst(regex, "");
				pos=pos.replaceFirst(regex, "");
				pos=pos.replaceFirst(regex, "");
				position+=3;
			}else if(pos.startsWith("d,v,")){
				$weibo.getWordStructure()[position]="d_v";
				$weibo.getWordStructure()[position+1]="d_v";
				pos=pos.replaceFirst(regex, "");
				pos=pos.replaceFirst(regex, "");
				position+=2;
			}else if(pos.startsWith("d,vi,")){
				$weibo.getWordStructure()[position]="d_vi";
				$weibo.getWordStructure()[position+1]="d_vi";
				pos=pos.replaceFirst(regex, "");
				pos=pos.replaceFirst(regex, "");
				position+=2;
			}else if(pos.startsWith("n,a,")){
				$weibo.getWordStructure()[position]="n_a";
				$weibo.getWordStructure()[position+1]="n_a";
				pos=pos.replaceFirst(regex, "");
				pos=pos.replaceFirst(regex, "");
				position+=2;
			}else if(pos.startsWith("n,v,")){
				$weibo.getWordStructure()[position]="n_v";
				$weibo.getWordStructure()[position+1]="n_v";
				pos=pos.replaceFirst(regex, "");
				pos=pos.replaceFirst(regex, "");
				position+=2;
			}else if(pos.startsWith("n,vi,")){
				$weibo.getWordStructure()[position]="n_vi";
				$weibo.getWordStructure()[position+1]="n_vi";
				pos=pos.replaceFirst(regex, "");
				pos=pos.replaceFirst(regex, "");
				position+=2;
			}else if(pos.startsWith("v,a,")){
				$weibo.getWordStructure()[position]="v_a";
				$weibo.getWordStructure()[position+1]="v_a";
				pos=pos.replaceFirst(regex, "");
				pos=pos.replaceFirst(regex, "");
				position+=2;
			}else if(pos.startsWith("v,d,vf,")){
				$weibo.getWordStructure()[position]="v_d_vf";
				$weibo.getWordStructure()[position+1]="v_d_vf";
				$weibo.getWordStructure()[position+2]="v_d_vf";
				pos=pos.replaceFirst(regex, "");
				pos=pos.replaceFirst(regex, "");
				pos=pos.replaceFirst(regex, "");
				position+=3;
			}else if(pos.startsWith("v,n,")){
				$weibo.getWordStructure()[position]="v_n";
				$weibo.getWordStructure()[position+1]="v_n";
				pos=pos.replaceFirst(regex, "");
				pos=pos.replaceFirst(regex, "");
				position+=2;
			}else if(pos.startsWith("v,y,")){
				$weibo.getWordStructure()[position]="v_y";
				$weibo.getWordStructure()[position+1]="v_y";
				pos=pos.replaceFirst(regex, "");
				pos=pos.replaceFirst(regex, "");
				position+=2;
			}else if(pos.startsWith("v,d,y,")){
				$weibo.getWordStructure()[position]="v_d_y";
				$weibo.getWordStructure()[position+1]="v_d_y";
				$weibo.getWordStructure()[position+2]="v_d_y";
				pos=pos.replaceFirst(regex, "");
				pos=pos.replaceFirst(regex, "");
				pos=pos.replaceFirst(regex, "");
				position+=3;
			}else if(pos.startsWith("v,u,d,a,")){
				$weibo.getWordStructure()[position]="v_u_d_a";
				$weibo.getWordStructure()[position+1]="v_u_d_a";
				$weibo.getWordStructure()[position+2]="v_u_d_a";
				$weibo.getWordStructure()[position+3]="v_u_d_a";
				pos=pos.replaceFirst(regex, "");
				pos=pos.replaceFirst(regex, "");
				pos=pos.replaceFirst(regex, "");
				pos=pos.replaceFirst(regex, "");
				position+=4;
			}else if(pos.startsWith("vyou,n,")){
				$weibo.getWordStructure()[position]="vyou_n";
				$weibo.getWordStructure()[position+1]="vyou_n";
				pos=pos.replaceFirst(regex, "");
				pos=pos.replaceFirst(regex, "");
				position+=2;
			}else{
				pos=pos.replaceFirst(regex, "");
				position++;
			}
		}
		/*
		for(int i=0;i<wordCount;i++){
			if(pos.startsWith("a,a", i)){
				$weibo.getWordStructure()[i/2]="a_a";
				$weibo.getWordStructure()[i/2+1]="a_a";
				i+=2;
			}else if(pos.startsWith("a,n", i)){
				$weibo.getWordStructure()[i/2]="a_n";
				$weibo.getWordStructure()[i/2+1]="a_n";
				i+=2;
			}else if(pos.startsWith("d,a,y", i)){
				$weibo.getWordStructure()[i/2]="d_a_y";
				$weibo.getWordStructure()[i/2+1]="d_a_y";
				$weibo.getWordStructure()[i/2+2]="d_a_y";
				i+=4;
			}else if(pos.startsWith("d,a", i)){
				$weibo.getWordStructure()[i/2]="d_a";
				$weibo.getWordStructure()[i/2+1]="d_a";
				i+=2;
			}else if(pos.startsWith("d,an", i)){
				$weibo.getWordStructure()[i/2]="d_an";
				$weibo.getWordStructure()[i/2+1]="d_an";
				i+=2;
			}else if(pos.startsWith("d,v,y", i)){
				$weibo.getWordStructure()[i/2]="d_v_y";
				$weibo.getWordStructure()[i/2+1]="d_v_y";
				$weibo.getWordStructure()[i/2+2]="d_v_y";
				i+=4;
			}else if(pos.startsWith("d,v", i)){
				$weibo.getWordStructure()[i/2]="d_v";
				$weibo.getWordStructure()[i/2+1]="d_v";
				i+=2;
			}else if(pos.startsWith("d,vi", i)){
				$weibo.getWordStructure()[i/2]="d_vi";
				$weibo.getWordStructure()[i/2+1]="d_vi";
				i+=2;
			}else if(pos.startsWith("n,a", i)){
				$weibo.getWordStructure()[i/2]="n_a";
				$weibo.getWordStructure()[i/2+1]="n_a";
				i+=2;
			}else if(pos.startsWith("n,v", i)){
				$weibo.getWordStructure()[i/2]="n_v";
				$weibo.getWordStructure()[i/2+1]="n_v";
				i+=2;
			}else if(pos.startsWith("n,vi", i)){
				$weibo.getWordStructure()[i/2]="n_vi";
				$weibo.getWordStructure()[i/2+1]="n_vi";
				i+=2;
			}else if(pos.startsWith("v,a", i)){
				System.out.println(i/2);
				$weibo.getWordStructure()[i/2]="v_a";
				$weibo.getWordStructure()[i/2+1]="v_a";
				i+=2;
			}else if(pos.startsWith("v,d,vf", i)){
				$weibo.getWordStructure()[i/2]="v_d_vf";
				$weibo.getWordStructure()[i/2+1]="v_d_vf";
				$weibo.getWordStructure()[i/2+2]="v_d_vf";
				i+=4;
			}else if(pos.startsWith("v,n", i)){
				$weibo.getWordStructure()[i/2]="v_n";
				$weibo.getWordStructure()[i/2+1]="v_n";
				i+=2;
			}else if(pos.startsWith("v,y", i)){
				$weibo.getWordStructure()[i/2]="v_y";
				$weibo.getWordStructure()[i/2+1]="v_y";
				i+=2;
			}else if(pos.startsWith("v,d,y", i)){
				$weibo.getWordStructure()[i/2]="v_d_y";
				$weibo.getWordStructure()[i/2+1]="v_d_y";
				$weibo.getWordStructure()[i/2+2]="v_d_y";
				i+=4;
			}else if(pos.startsWith("v,u,d,a", i)){
				$weibo.getWordStructure()[i/2]="v_u_d_a";
				$weibo.getWordStructure()[i/2+1]="v_u_d_a";
				$weibo.getWordStructure()[i/2+2]="v_u_d_a";
				$weibo.getWordStructure()[i/2+3]="v_u_d_a";
				i+=6;
			}else if(pos.startsWith("vyou,n", i)){
				$weibo.getWordStructure()[i/2]="vyou_n";
				$weibo.getWordStructure()[i/2+1]="vyou_n";
				i+=2;
			}
		}*/
	}
}
