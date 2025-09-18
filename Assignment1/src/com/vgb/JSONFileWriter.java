package com.vgb;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/*
 * Gavin Blesh
 * 
 * 2025-05-02
 * 
 * Program to write to a JSONFile
 */
public class JSONFileWriter {

	/*
	 * Writes fileContents to a JSON folder. It is used in DataConverter after the
	 * data has been converted.
	 */
	public static void fileWriter(String fileName, String fileContents) {

		File f = new File(fileName);

		try {
			PrintWriter pw = new PrintWriter(f);
			pw.println(fileContents);
			pw.close();

		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
}
