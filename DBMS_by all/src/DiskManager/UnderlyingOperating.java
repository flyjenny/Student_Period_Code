package DiskManager;

import java.io.*;

/**
 * @author BennyChan
 *最底层操作，利用RandomAccessFile对磁盘进行读写
 */
public class UnderlyingOperating {
	private String $path;// disk path
	private RandomAccessFile $disk;

	public static int filesize = 0; /* Total number of pages */
	public final static int pagesize = 4096; /* 4KB per page */

	UnderlyingOperating(String filepath) {
		$path = filepath;
		$disk = null;
	}

	/**
	 * @return 磁盘页数
	 */
	public int get_filesize() {
		return filesize;
	}

	public RandomAccessFile get_file() {
		return $disk;
	}

	/**
	 * @return 磁盘大小
	 * @throws IOException
	 * 打开磁盘
	 */
	public int disk_open() throws IOException {
		$disk = new RandomAccessFile($path, "rw");
		filesize = ((int) ($disk.length())) / pagesize;
		return filesize;
	}

	/**
	 * 根据页号从磁盘读入数据，存入bytebuffer
	 * @param pageNum
	 * @param bytebuffer
	 * @return 1 for successful,-1 for failure. 
	 */
	public int read_page(int pageNum, byte[] bytebuffer) {
		if (pageNum >= filesize) {
			System.out.println("Block " + pageNum
					+ " requirement is out of file size");
			return -1;
		}
		try {
			$disk.seek(pageNum * pagesize);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
		try {
			$disk.read(bytebuffer);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
		return 1;
	}

	/**
	 * 根据页号把bytebuffer中内容写入磁盘
	 * @param pageNum
	 * @param bytebuffer
	 * @return 1 for successful,-1 for failure. 
	 */
	public int write_page(int pageNum, String bytebuffer) {
		String buffer;
		if (bytebuffer.length() > pagesize) {
			buffer = bytebuffer.substring(0, pagesize);
		} else {
			buffer = bytebuffer;
		}

		if (pageNum >= filesize) {
			System.out.println("Block " + pageNum
					+ " requirement is out of file size");
			return -1;
		}
		try {
			$disk.seek(pageNum * pagesize);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}

		try {
			$disk.writeBytes(buffer);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
		return 1;
	}

	/**
	 * @return 1 for successful,-1 for failure. close disk
	 */
	public int close_disk() {
		try {
			$disk.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
		return 1;
	}

	public static void main(String args[]) throws IOException {
		UnderlyingOperating ran = new UnderlyingOperating("myfile");
		ran.disk_open();
		System.out.println("The file has " + ran.get_filesize() + " page");
		byte buffer[] = new byte[4096];

		String str = "1024 STORY Third Series i Edited by W0DRW WYATT COLLINS 48 PALL MALL LONDON 194 THIS BOOK "
				+ "IS SET IN FONTANA, A NEW TYPE FACE DESIGNED FOR THE EXCLUSIVE USE OF THE HOUSE OF COLUNS, AND PRINTED "
				+ "BY THEM IN GREAT BRITAIN COLLINS CLEAR-TYPE PRESS LONDON AND GLASGOW COPYRIGHT 1942 ADVISORY COMMITTEE . H . E . "
				+ "BATES LESLIE HALWARD L. A. PAVEY GEOFFREY WEST Note THE objects of English Story are threefold i To provide a new outlet "
				+ "for the short story-in England, unfettered by standardised requirements of length, theme or treatment ii To encourage the "
				+ "young writer who, under present conditions, finds it difficult to see his work published iii To bring to the general reading "
				+ "public a half-yearly collection in book form of the most interesting new work of contemporary short story writers. "
				+ "English Story is run on non-commercial lines. That is to say, the first to benefit from the profits are the contributors. "
				+ "Only previously unpublished work by British subjects is accepted. Stories of one to ten thousand words are invited and should "
				+ "be sent to English Story, I End House, Rosemoor Street, London, S. W. 3, accompanied by a stamped and addressed envelope. "
				+ "Payment will be made for those published. EDWARD J. OBRIEN SHORT STORY PRIZE The Edward J. O'Brien Short Story Prize, "
				+ "commemorating the late Edward O'Brien and his work for the short story, is awarded annually for the best short story to "
				+ "appear in either of the two series of English Story published each year. Its value is Ten Pounds. Contents AMONG THE PIGEONS "
				+ "A FOREWORD THE TOURNAMENT William Sansom CONVERSATIONS IN EBURY STREET RICH RELATIONS GUILT HAPPY AS THE DAY IS LONG SWING "
				+ "BOITE THE SHRINE SACK LABOURER THE DREAMERS STAGGERED HOLIDAYMARRIED MAN LAND OF PROMISE THE WAYWARD ASS MISS PEARL THE FAILURE "
				+ "A MEETING IN BEDLAM NEITHER YOU NOR I ., THE LEVEL-CROSSING A GOOD LUNCH N PINK MAY Charles Furbank Nigel Heseltine "
				+ "Ronald Willetts PAGE 9 16 24 36 4 y. Maclaren-Ross 46 Vivian Connell 60 Elizabeth Berridge 64 Raymond Williams 71 Gwyn Jones 79 "
				+ "Osbert Sitwell 89 L. A. Pavey 103 Dorothy Baker no Diarmuid Kelly 119 Henry Treece 124 Reginald Moore s 135 L. y. Daventry 151 "
				+ "Elisabeth Kyle 157 Sylvia Townsend Warner 162 Nicholas Mtore 176 Elizabeth Bowen 184 AMONG THE PIGEONS A FOREWORD IN THE spring "
				+ "of 1940 preparations were begun for the first series of English Story. By that time indications of the way in which the War was "
				+ "going to influence writers were coming through. The main interest lay in whether or not the War would break the domination over "
				+ "writing which the Left-wing Group of Auder, Ralph Bates, Isherwood, MacNeice, Spender, Upward, and the rest, and by virtue of his "
				+ "hangover influence, T. S. Eliot, had held for the last ten years or so. The chief characteristics of this loosely defined group "
				+ "can be summarised. They arc applicable not so much to individual writers but to the spirit emanating from the group as a whole. "
				+ "First, there was the fetish of realism or factual authenticity. This produced a situation in which miners, dockyard workers, factory "
				+ "workers had the monopoly of writing about their callings and were prohibited from writing about anything else, thus dispensing with "
				+ "the need for imagination. Writing became a documentation of selected incidents in the authors life or the people known to him. "
				+ "The result as displayed in New Writing and similarly inclined periodicals was lacking in richness, often flat anduninteresting, "
				+ "and sometimes read like an out-of-date news magazine. It was objectivity ad absurdum. The heroes of Gordon Jeffrey, B. L. Coombes, "
				+ "Willy Goldman, John Sommerfield and kindred writers are those of fact, barely touched by the imagination. The claim of writers of this "
				+ "type to be creative has little foundation. They do not create at all they record and retail atmospheres and experiences as highbrow "
				+ "reporters, without controlling or adding to them...";
		
//		ran.write_page(1024, str);
//		ran.read_page(0, buffer);
		OutputStreamWriter file=new OutputStreamWriter(new FileOutputStream("t.txt"));
		for(int i=0;i<(257+52);i++){
			ran.read_page(i, buffer);
			String abc = new String(buffer);
			file.write(abc+"\r\n");
//			System.out.println(i+abc);
		}
		file.flush();
		file.close();
		
/*		String abc = new String(buffer);
		System.out.println(abc);
*/
		
		/*for(int i=0;i<100;i++){
		ran.read_page(i, b);
		dis=new String(b,"8859_1");
		System.out.println(dis);
		}*/
		
		/*
		 * char a[]=new char[10]; String test="hello/"; String[]
		 * chen=test.split("/");
		 * 
		 * 
		 * 
		 * FileStruct fs1=new FileStruct('y',"table1",1,50); FileStruct fs2=new
		 * FileStruct('n',"table2",51,50);
		 * 
		 * ran.read_page(0, buffer); String str1=new String(buffer);
		 */
	}
}
