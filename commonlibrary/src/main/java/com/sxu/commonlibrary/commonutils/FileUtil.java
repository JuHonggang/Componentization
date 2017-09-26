package com.sxu.commonlibrary.commonutils;

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Copyright (c) 2017. zhinanmao Inc. All rights reserved.
 * <p>
 * <p>
 * 类或接口的描述信息
 *
 * @author Freeman
 * @date 17/6/20
 */


public class FileUtil {

	public static boolean SDCardIsValid() {
		return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
	}

	public static boolean fileIsExist(String filePath) {
		if (SDCardIsValid()) {
			File file = new File(filePath);
			if (file.exists()) {
				return true;
			}
		}

		return false;
	}

	public static void saveFile(String filePath, String fileName, String content) {
		if (SDCardIsValid()) {
			File directory = new File(filePath);
			if (!directory.exists()) {
				directory.mkdirs();
			}
			File file = new File(filePath, fileName);
			FileOutputStream outputStream = null;
			try {
				outputStream = new FileOutputStream(file);
				outputStream.write(content.getBytes());
				outputStream.flush();
			} catch (Exception e) {
				e.printStackTrace(System.out);
			} finally {
				if (outputStream != null) {
					try {
						outputStream.close();
					} catch (Exception e) {
						e.printStackTrace(System.out);
					}
				}
			}
		}
	}

	public static void deleteFile(String filePath, String fileName) {
		if (SDCardIsValid()) {
			String path = filePath + "/" + fileName;
			File file = new File(path);
			if (file.exists()) {
				file.delete();
			}
		}
	}

	public static String readFile(String filePath, String fileName) {
		String content = null;
		if (SDCardIsValid()) {
			String path = null;
			if (filePath.endsWith("/")) {
				path = filePath + fileName;
			} else {
				path = filePath + "/" + fileName;
			}
			File file = new File(path);
			if (file.exists()) {
				FileInputStream inputStream = null;
				try {
					inputStream = new FileInputStream(file);
					int len = inputStream.available();
					byte[] buffer = new byte[len];
					inputStream.read(buffer);
					content = buffer.toString();
				} catch (Exception e) {
					if (inputStream != null) {
						try {
							inputStream.close();
						} catch (Exception e1) {
							e1.printStackTrace(System.out);
						}
					}
				}

			}

		}

		return content;
	}

	public static int getFileSize(String filePath, String fileName) {
		if (SDCardIsValid()) {
			String path = filePath + "/" + fileName;
			File file = new File(path);
			if (file.exists()) {
				file.getTotalSpace();
			}
		}

		return 0;
	}

}
