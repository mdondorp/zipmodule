/**
 * This file was auto-generated by the Titanium Module SDK helper for Android
 * Appcelerator Titanium Mobile
 * Copyright (c) 2009-2010 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 *
 */
package com.logic8.zipmodule;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.appcelerator.kroll.KrollModule;
import org.appcelerator.kroll.annotations.Kroll;
import org.appcelerator.titanium.TiApplication;
import org.appcelerator.kroll.common.Log;

import android.os.Environment;

@Kroll.module(name = "Zipmodule", id = "com.logic8.zipmodule")
public class ZipmoduleModule extends KrollModule {

	// Standard Debugging variables
	private static final String LCAT = "ZipmoduleModule";
	private static final int BUFFER = 2048;

	static List<String> filelist = new ArrayList<String>();

	public ZipmoduleModule() {
		super();
	}

	@Kroll.onAppCreate
	public static void onAppCreate(TiApplication app) {
	}

	@Kroll.method
	public String zipADir(String sourceDir, String uniqueid, String username,
			String targetFile) {
		try {
			// create byte buffer
			byte[] buffer = new byte[1024];

			// create object of FileOutputStream
			FileOutputStream fout = new FileOutputStream(targetFile);

			// create object of ZipOutputStream from FileOutputStream
			ZipOutputStream zout = new ZipOutputStream(fout);
			zout.putNextEntry(new ZipEntry(uniqueid + "/"));
			zout.putNextEntry(new ZipEntry(uniqueid + "/" + username + "/"));

			// create File object from directory name
			File dir = new File(sourceDir);

			// check to see if this directory exists
			if (!dir.isDirectory()) {
				System.out.println(sourceDir + " is not a directory");
			} else {
				File[] files = dir.listFiles();
				if(files.length == 0){
					System.out.println("No files in " + sourceDir);
				}
				for (int i = 0; i < files.length; i++) {
					if (!files[i].isDirectory()) {
						System.out.println("Adding " + files[i].getName());
						// create object of FileInputStream for source file
						FileInputStream fin = new FileInputStream(files[i]);

						zout.putNextEntry(new ZipEntry(uniqueid + "/"
								+ username + "/" + files[i].getName()));

						int length;

						while ((length = fin.read(buffer)) > 0) {
							zout.write(buffer, 0, length);
						}

						zout.closeEntry();

						// close the InputStream
						fin.close();
					} else {
						System.out.println("isDirectory!");

						File[] subfiles = files[i].listFiles();

						for (int j = 0; j < subfiles.length; j++) {
							System.out.println("Adding "
									+ subfiles[j].getName());
							// create object of FileInputStream for source file
							FileInputStream fin = new FileInputStream(
									subfiles[j]);

							zout.putNextEntry(new ZipEntry(uniqueid + "/"
									+ username + "/" + files[i].getName() + "/"
									+ subfiles[j].getName()));

							int length;

							while ((length = fin.read(buffer)) > 0) {
								zout.write(buffer, 0, length);
							}

							zout.closeEntry();

							// close the InputStream
							fin.close();
						}
					}

				}
			}

			// close the ZipOutputStream
			zout.close();

			System.out.println("Zip file has been created!");
			return "success";

		} catch (IOException ioe) {
			System.out.println("IOException :" + ioe);
			return "false";
		}
	}

	@SuppressWarnings("resource")
	@Kroll.method
	public String splitZip(String filename, String chunkSizeStr) {
		try {
			int chunkSize = Integer.parseInt(chunkSizeStr);

			BufferedInputStream in = new BufferedInputStream(
					new FileInputStream(filename));

			File f = new File(filename);
			long fileSize = f.length();

			if ((int) fileSize < chunkSize) {
				return "TooSmallToSplit";
			}
			String fileName = filename.substring(0, filename.length() - 4);
			// loop for each full chunk
			int subfile;
			for (subfile = 1; subfile <= fileSize / chunkSize; subfile++) {
				// open the output file
				BufferedOutputStream out = new BufferedOutputStream(
						new FileOutputStream(fileName + "." + subfile));

				// write the right amount of bytes
				for (int currentByte = 0; currentByte < chunkSize; currentByte++) {
					// load one byte from the input file and write it to the
					// output file
					out.write(in.read());
				}

				// close the file
				out.close();
			}

			// loop for the last chunk (which may be smaller than the chunk
			// size)
			if (fileSize != chunkSize * (subfile - 1)) {
				// open the output file
				BufferedOutputStream out = new BufferedOutputStream(
						new FileOutputStream(fileName + ".e"));

				// write the rest of the file
				int b;
				while ((b = in.read()) != -1)
					out.write(b);

				// close the file
				out.close();
			}

			// close the file
			in.close();
			return "success";
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "false";
		}
	}

	@Kroll.method
	public void zipADirOud(String sourceDir, String uniqueid, String username,
			String targetFile) {
		try {
			// create object of FileOutputStream
			FileOutputStream fout = new FileOutputStream(targetFile);

			// create object of ZipOutputStream from FileOutputStream
			ZipOutputStream zout = new ZipOutputStream(
					new BufferedOutputStream(fout));

			// create File object from source directory
			File fileSource = new File(sourceDir);
			zout.putNextEntry(new ZipEntry(uniqueid + "/"));
			zout.putNextEntry(new ZipEntry(uniqueid + "/" + username + "/"));
			zout.putNextEntry(new ZipEntry(uniqueid + "/" + username + "/"
					+ fileSource.getName()));
			FileInputStream fin = new FileInputStream(fileSource);
			for (int c = fin.read(); c != -1; c = fin.read()) {
				zout.write(c);
			}
			fin.close();
			fout.close();
			zout.closeEntry();
			// close the ZipOutputStream
			zout.close();

			System.out.println("Zip file has been created!");

		} catch (IOException ioe) {
			System.out.println("IOException :" + ioe);
		}
	}

	@Kroll.method
	public static void addDirectory(ZipOutputStream zout, File fileSource) {

		// get sub-folder/files list
		File[] files = fileSource.listFiles();
		System.out.println("files: " + files.length);
		System.out.println("Adding directory " + fileSource.getName());

		for (int i = 0; i < files.length; i++) {
			// if the file is directory, call the function recursively
			if (files[i].isDirectory()) {
				addDirectory(zout, files[i]);
				continue;
			}

			/*
			 * we are here means, its file and not directory, so add it to the
			 * zip file
			 */

			try {
				System.out.println("Adding file " + files[i].getName());
				int buffersize = 1024;
				// create byte buffer
				byte[] buffer = new byte[buffersize];

				// create object of FileInputStream
				FileInputStream fin = new FileInputStream(files[i]);

				zout.putNextEntry(new ZipEntry(files[i].getName()));

				/*
				 * After creating entry in the zip file, actually write the
				 * file.
				 */
				int length;
				int fileLength = (int) files[i].length();
				int bytesRemaining = fileLength;
				while ((length = fin.read(buffer)) > 0) {
					bytesRemaining -= length;
					System.out.println("bytesRemaining: " + bytesRemaining);
					System.out.println("write " + buffer.length + " bytes");
					zout.write(buffer, 0, buffer.length);
					if (bytesRemaining < buffersize) {
						buffer = new byte[bytesRemaining];
					}
				}

				/*
				 * After writing the file to ZipOutputStream, use
				 * 
				 * void closeEntry() method of ZipOutputStream class to close
				 * the current entry and position the stream to write the next
				 * entry.
				 */

				zout.closeEntry();

				// close the InputStream
				fin.close();

			} catch (IOException ioe) {
				System.out.println("IOException :" + ioe);
			}
		}
	}

	@Kroll.method
	public String zip(String[] _files, String _zipFile) {
		System.out.println(Environment.getDataDirectory().getAbsolutePath());
		System.out.println("files: " + _files.length);
		try {
			BufferedInputStream origin = null;
			FileOutputStream dest = new FileOutputStream(_zipFile);

			ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(
					dest));

			byte data[] = new byte[BUFFER];

			for (int i = 0; i < _files.length; i++) {
				Log.v("Compress", "Adding: " + _files[i]);
				FileInputStream fi = new FileInputStream(_files[i]);
				origin = new BufferedInputStream(fi, BUFFER);
				ZipEntry entry = new ZipEntry(_files[i].substring(_files[i]
						.lastIndexOf("/") + 1));
				out.putNextEntry(entry);
				int count;
				while ((count = origin.read(data, 0, BUFFER)) != -1) {
					out.write(data, 0, count);
				}
				origin.close();
			}

			out.close();
			return (Environment.getDataDirectory().getAbsolutePath());
		} catch (Exception e) {
			e.printStackTrace();
			return (Environment.getDataDirectory().getAbsolutePath());
		}

	}

	@Kroll.method
	public String zipDirectory(String dir, String zipPath) throws IOException {
		File directory = new File(dir);
		if (!directory.exists()) {
			return "Directory does not exists!!";
		}
		File zip = new File(zipPath);

		ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zip));
		String s = zipFiles(directory, directory, zos);

		zos.close();
		return s;
	}

	@Kroll.method
	public String zipFiles(File directory, File base, ZipOutputStream zos)
			throws IOException {
		String length = "";
		File[] files = directory.listFiles();
		byte[] readBuffer = new byte[2156];
		int bytesIn = 0;
		for (int i = 0, n = files.length; i < n; i++) {
			if (files[i].isDirectory()) {
				zipFiles(files[i], base, zos);
			} else {
				System.out.println("file: " + files[i].length());
				FileInputStream fis = new FileInputStream(files[i]);
				// create a new zip entry
				ZipEntry anEntry = new ZipEntry(files[i].getName());
				// place the zip entry in the ZipOutputStream object
				zos.putNextEntry(anEntry);
				// now write the content of the file to the ZipOutputStream
				while ((bytesIn = fis.read(readBuffer)) != -1) {
					zos.write(readBuffer, 0, bytesIn);
				}
				// close the Stream
				fis.close();
			}
		}
		return "files: " + length;
	}

	// Properties
	@Kroll.getProperty
	public String getExampleProp() {
		Log.d(LCAT, "get example property");
		return "hello world";
	}

	@Kroll.setProperty
	public void setExampleProp(String value) {
		Log.d(LCAT, "set example property: " + value);
	}

	@Kroll.method
	public void generateFileList(String srcfolder) {
		System.out.println(srcfolder);
		File srcFolder = new File(srcfolder);
		// add file only
		if (srcFolder.isFile()) {
			// String filename = srcfolder.substring(srcfolder.length()+1,
			// srcfolder.length());
			filelist.add(srcfolder);
		} else if (srcFolder.isDirectory()) {
			String[] subNote = srcFolder.list();
			for (String filename : subNote) {
				generateFileList(srcFolder.getAbsolutePath() + "/" + filename);
			}
		}
	}

	@Kroll.method
	public void zipIt(String zipFile) {
		byte[] buffer = new byte[1024];

		try {

			FileOutputStream fos = new FileOutputStream(zipFile);
			ZipOutputStream zos = new ZipOutputStream(fos);

			System.out.println("Output to Zip : " + zipFile);
			int counter = 0;
			for (String file : filelist) {

				System.out.println("File Added : " + file);
				ZipEntry ze = new ZipEntry(file);
				zos.putNextEntry(ze);

				FileInputStream in = new FileInputStream(filelist.get(counter));

				int len;
				while ((len = in.read(buffer)) > 0) {
					zos.write(buffer, 0, len);
				}
				counter++;
				in.close();
			}

			zos.closeEntry();
			// remember close it
			zos.close();

			System.out.println("Done");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
