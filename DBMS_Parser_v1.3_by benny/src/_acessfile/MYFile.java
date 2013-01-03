package _acessfile;
public class MYFile{
	public MYFile(int p,int filesize){
		$absposition = p;
		$filesize = filesize;
		$buffer = new byte[filesize%(8*4096)==0?(filesize/(8*4096)*4096+4096): ((filesize/(8*4096)+1)*4096+4096)];
	}
	public int $filesize;
	public int $absposition;
	public byte[] $buffer;
	
}