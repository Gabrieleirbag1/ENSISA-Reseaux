package fr.ensisa.hassenforder.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileHelper {

	private static String rootDirname = ".";

	/*
	 * function used to guess the location of the images dir.
	 * application launched using the CLI should have the images dir just in the local dir
	 * application launched using the run from VSC the local dir is the parent dir
	 */

	public static void guessSrcDir (String appName) {
		File localDir = new File(".");
		String fullName = localDir.getAbsolutePath();
		try {
			fullName = localDir.getCanonicalPath();
		} catch (IOException exception) {
			exception.printStackTrace();			
		}
		int position = fullName.lastIndexOf(File.separator);
		if (position != -1) {
			String lastName = fullName.substring(position+1);
			// we are not in the good dir
			if (! appName.equals(lastName)) {
				// we are in the parent dir
				File appendApp = new File(localDir, appName);
				if (appendApp.exists()) rootDirname=appName;
			}
		}
		File rootDir = new File(rootDirname);
		System.out.println("RootDir : " + rootDir.getAbsolutePath());
	}	

	public static String [] getDirectory () {
		File directory = new File (rootDirname);
		String [] content = directory.list();
		return content;
	}

	public static long getFileSize (String filename) {
		File directory = new File (rootDirname);
		File localFile = new File (directory, filename);
		long length = -1;
		if (localFile.exists()) {
			length = localFile.length();
		}
		return length;
	}

	public static byte [] readContent (String filename) {
		if (filename == null) return null;
		byte [] content = null;
		try {
			File directory = new File (rootDirname);
			File localFile = new File (directory, filename);
			if (! localFile.exists()) return null;
			long length = localFile.length();
			if (length != 0) {
				content = new byte [(int) length];
				FileInputStream fis = new FileInputStream (localFile);
				BufferedInputStream bis = new BufferedInputStream (fis);
				bis.read(content);
				bis.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content;
	}

	public static void writeContent (String filename, byte [] content) {
		if (content == null) return ;
		try {
			File directory = new File (rootDirname);
			File localFile = new File (directory, filename);
			localFile.getParentFile().mkdirs();
			FileOutputStream fos = new FileOutputStream (localFile);
			BufferedOutputStream bos = new BufferedOutputStream (fos);
			bos.write(content);
			bos.close();
		} catch (IOException e) {
		}
	}

	public static boolean fileExists(String filename) {
		File directory = new File (rootDirname);
		File localFile = new File (directory, filename);
		return localFile.exists();
	}
}
