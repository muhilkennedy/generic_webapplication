package com.platform.util;

import java.io.File;
import java.io.IOException;
import java.net.URLConnection;

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
			Log.platform.warn("Removing Dir - " + dir.getPath());
			return dir.delete();
		}
		return false;
	}
	
	public static String getFileExtensionFromName(String fileName) {
		return fileName != null ? fileName.substring(fileName.lastIndexOf(".") + 1) : null;
	}

	public static File createTempFile(String prefix, String suffix) throws IOException {
		return File.createTempFile(prefix, suffix);
	}

	public static File createTempFile(String prefix, String suffix, File directory) throws IOException {
		return File.createTempFile(prefix, suffix, directory);
	}

	public static File createTempFile() throws IOException {
		return File.createTempFile("temp", ".tmp");
	}
	
	public static String sanitizeDirPath(String dir) {
		StringBuffer buffer = new StringBuffer();
		if (dir.charAt(0) != File.separator.charAt(0)) {
			buffer.append(File.separator);
		}
		buffer.append(dir);
		if (dir.charAt(dir.length()-1) != File.separator.charAt(0)) {
			buffer.append(File.separator);
		}
		return buffer.toString();
	}

	public static String findContentTypeFromFileName(String fileName) {
		return URLConnection.guessContentTypeFromName(fileName);
	}
	
}
