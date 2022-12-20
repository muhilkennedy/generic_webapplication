package com.base.util;

import java.io.File;

public class FileUtil {
	
	/**
	 * @see This method needs to be called everytime after a temp file/Dir is
	 *      created in order keep the memory optimized.
	 * @param file to be deleted
	 * @return true if successfully removed.
	 */
	public static boolean deleteDirectoryOrFile(File dir) {
		if(dir != null) {
			if (dir.isDirectory()) {
				File[] children = dir.listFiles();
				for (int i = 0; i < children.length; i++) {
					boolean success = deleteDirectoryOrFile(children[i]);
					if (!success) {
						return false;
					}
				}
			}
			Log.base.warn("Removing Dir - " + dir.getPath());
			return dir.delete();
		}
		return false;
	}
	
	public static String getFileExtensionFromName(String fileName) {
		return fileName != null ? fileName.substring(fileName.lastIndexOf(".")+1) : null;
	}


}
