package _acessfile;

public class FileStruct {
	public char $flag;
	public String $filename;
	public int $startPage;
	public int $length;

	FileStruct(char flag, String filename, int startPage, int length) {
		$flag = flag;
		$filename = filename;
		$startPage = startPage;
		$length = length;
	}

	public String toString() {
		return $flag + String.format("%52s", $filename)
				+ String.format("%5s", $startPage)
				+ String.format("%5s", $length) + "/";
	}
}
