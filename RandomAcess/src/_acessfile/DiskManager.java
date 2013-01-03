package _acessfile;

import java.io.IOException;
import java.util.HashMap;
import java.math.*;

public class DiskManager {
	public DiskManager(String diskname){
		
		$disk=new UnderlyingOperating(diskname);
		try {
			$totalfilesize=$disk.disk_open();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		$buffer = new byte[$disk.pagesize];
		$disk.read_page(0, $buffer);
		$firstpage=new String($buffer);
		$filerecord=$firstpage.trim().split("/");
		$hm=new HashMap<String,Integer>();
		if($firstpage.lastIndexOf('/')!=-1){
			for(int j=0;j<$filerecord.length;j++){
				if($filerecord[j].substring(0,1).equals("y")){
					$hm.put($filerecord[j].substring(1,53).trim(), j);
				}
			}
		}
		$fi=null;
	}
	public int $totalfilesize;
	private UnderlyingOperating $disk;
	private byte $buffer[];
	private String[] $filerecord;
	private String $firstpage;
	private HashMap<String,Integer> $hm;
	private MYFile $fi;
	/*filesize is KB*/
	public String[] Get_record(){
		return $filerecord;
	}
	public HashMap<String,Integer> Get_Hm(){
		return $hm;
	}
	
	public boolean CreateFile(String fileName,int filesize){
		int length=filesize/4;
	
		if(fileName.length()>52){
			System.out.println("Error! Reach the limitation of filename length!");
			return false;
		}
		if($hm.containsKey(fileName)){
			System.out.println("Error! The DB "+fileName+" is exist!");
			return false;
		}
		if(length>($totalfilesize-1)){
			System.out.println("The require "+filesize+" kb is out of the size of disk!");
			return false;
		}
		if($hm.size()>=64){
			System.out.println("Error! It is out of the limitation of DB number");
			return false;
		}
		int position=$firstpage.lastIndexOf('/');
		FileStruct newfile=null;
		if(position==-1){/*No DB exist*/
			newfile=new FileStruct('y',fileName,1,length);
			$firstpage=newfile.toString();
			$filerecord=$firstpage.trim().split("/");
			$hm.put(fileName, 0);
			$disk.write_page(0, $firstpage);
			try {
				Runtime.getRuntime().exec("fsutil file setzerodata offset="+4096+" length="+length*4096+" D:\\Documents\\workspace\\RandomAcess\\myfile");

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		}
		else{
			
			StringBuffer sbuffer=new StringBuffer();
			String temp=$filerecord[$filerecord.length-1];
			if(temp.substring(0,1).equals("n")){
				if(($totalfilesize-Integer.parseInt(temp.substring(53,58).trim()))<length){
					System.out.println("No enough space. You can try to use TrimSpace() to get more space");
					return false;
				}
				else{
					newfile=new FileStruct('y',fileName,Integer.parseInt(temp.substring(53,58).trim()),length);
					for(int i=0;i<$filerecord.length-1;i++){
						sbuffer.append($filerecord[i]);
						sbuffer.append("/");
					}
					sbuffer.append(newfile.toString());
					$firstpage=sbuffer.toString();
					$filerecord=$firstpage.trim().split("/");
					$hm.put(fileName, $filerecord.length-1);
					try {
						Runtime.getRuntime().exec("fsutil file setzerodata offset="+4096*Integer.parseInt(temp.substring(53,58).trim())+" length="+length*4096+" D:\\Documents\\workspace\\RandomAcess\\myfile");

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					$disk.write_page(0, $firstpage);
					return true;
				}
			}
			else{
				if(($totalfilesize-Integer.parseInt(temp.substring(53,58).trim())-Integer.parseInt(temp.substring(58,63).trim()))<length){
					System.out.println("No enough space. You can try to use TrimSpace() to get more space");
					return false;
				}
				else{
					int absp=Integer.parseInt(temp.substring(53,58).trim())+Integer.parseInt(temp.substring(58,63).trim());
					newfile=new FileStruct('y',fileName,absp,length);
					for(int i=0;i<$filerecord.length;i++){
						sbuffer.append($filerecord[i]);
						sbuffer.append("/");
					}
					sbuffer.append(newfile.toString());
					$firstpage=sbuffer.toString();
					$filerecord=$firstpage.trim().split("/");
					$hm.put(fileName, $filerecord.length-1);
					try {
						Runtime.getRuntime().exec("fsutil file setzerodata offset="+absp*4096+" length="+length*4096+" D:\\Documents\\workspace\\RandomAcess\\myfile");

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					$disk.write_page(0, $firstpage);
					return true;
				}
			}
		}
	}
	public boolean DestroyFile(String fileName){
		
		if($hm.get(fileName)==null){
			System.out.println("The DataBase "+fileName+" doesn't exist");
			return false;
		}
		int p=$hm.get(fileName);
		
		StringBuffer sb=new StringBuffer();
		sb.append("n");
		sb.append($filerecord[p].subSequence(1, 63));
		$filerecord[p]=sb.toString();
		
		$hm.remove(fileName);
		$firstpage=new String();
		for(int i=0;i<$filerecord.length;i++){
			
			$firstpage=$firstpage+$filerecord[i]+"/";
		}
		
		$disk.write_page(0, $firstpage);
		return true;
	}
	public MYFile OpenFile (String fileName){
		if($hm.get(fileName)==null){
			System.out.println("The DataBase "+fileName+" doesn't exist");
			return $fi;
		}
		int p=$hm.get(fileName);
		int absp=Integer.parseInt($filerecord[p].substring(53,58).trim());
		int length=Integer.parseInt($filerecord[p].substring(58,63).trim());
		$fi =new MYFile(absp,length);
		$disk.read_page(absp, $fi.$buffer);
		return $fi;
	}
	public boolean CloseFile(MYFile fileHandle){
		if(fileHandle==null){
			System.out.println("fileHandle is null");
			return false;
		}
		if($fi.equals(fileHandle)){
			$fi=null;
			return true;
		}
		return false;
	}
	public boolean WritePage(int page,String bytebuffer){
		if($fi==null){
			System.out.println("Please OpenFile firtst!");
			return false;
		}
		if(page>=$fi.$filesize){
			System.out.println("Error! Out of the current DB boundary!");
			return false;
		}
		if($disk.write_page($fi.$absposition+page, bytebuffer)<0){
			return false;
		}
		return true;
	}
	public boolean ReadPage(int page,byte[] bytebuffer){
		if($fi==null){
			System.out.println("Please OpenFile firtst!");
			return false;
		}
		if(page>=$fi.$filesize){
			System.out.println("Error! Out of the current DB boundary!");
			return false;
		}
		if($disk.read_page($fi.$absposition+page, bytebuffer)<0){
			return false;
		}
		return true;
	}
	
	public boolean WriteData(int byteNum,String data){
		int pageNum=byteNum/4096;
		
		if($fi==null){
			System.out.println("Please OpenFile firtst!");
			return false;
		}
		byte[] buffer=new byte[4096];
		ReadPage(pageNum,buffer);
		String handle=new String(buffer);
		String result;
		int relativeposition=byteNum%4096;
		
		if(data.length()>(4096-relativeposition)){
			System.out.println("Warning! The length of data is out of the boudary of one page. It will be cut!");
			result=handle.substring(0,relativeposition)+data.substring(0, 4096-relativeposition);
			WritePage(pageNum,result);
		}
		else{
			result=handle.substring(0, relativeposition)+data+handle.substring(relativeposition, 4096);
			WritePage(pageNum,result);
		}
		return true;
	}
	public int TrimSpace(){
		return 1;
	}
	
	public static void main(String args[]){
		DiskManager dm=new DiskManager("D:/Documents/workspace/DBMS/myfile");
		System.out.println(dm.$totalfilesize);
		for(int i=0;i<64;i++)
			dm.CreateFile("test"+i, 1024);

	}
}

class FileStruct{
	FileStruct(char flag,String filename,int startPage,int length){
		$flag=flag;
		$filename=filename;
		$startPage=startPage;
		$length=length;
	}
	public char $flag;
	public String $filename;
	public int $startPage;
	public int $length;
	public String toString(){
		return $flag+String.format("%52s", $filename)+String.format("%5s",$startPage)+String.format("%5s",$length)+"/";
	}
}



