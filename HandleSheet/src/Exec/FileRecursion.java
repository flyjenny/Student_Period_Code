package Exec;

import java.io.File;
import java.util.Vector;

public class FileRecursion {
	public Vector<File> vecFile;

	FileRecursion(String root) {
		vecFile = new Vector<File>();
		recursion(root, vecFile);
	}

	public void recursion(String root, Vector<File> vecFile) {
		File file = new File(root);
		File[] subFile = file.listFiles();
		for (int i = 0; i < subFile.length; i++) {
			if (subFile[i].isDirectory()) {
				recursion(subFile[i].getAbsolutePath(), vecFile);
			} else {
				// String filename = subFile[i].getName();
				vecFile.add(subFile[i]);
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		FileRecursion fr=new FileRecursion("R:/Score");
		for (File file : fr.vecFile) {  
            System.out.println(file.getAbsolutePath());  
        }
		
	}

}