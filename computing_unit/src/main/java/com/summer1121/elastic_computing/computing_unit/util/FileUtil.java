package com.summer1121.elastic_computing.computing_unit.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * 描述：文件工具
 *
 * @author xtysummer1121@foxmail.com
 * @date 2020/5/16
 */
public class FileUtil {


	/**
	 * 根据url，返回目标字节码
	 *
	 * @param url
	 * @return {@link byte[]}
	 * @className FileUtil
	 * @author xtysummer1121@foxmail.com
	 * @date 2020/5/16
	 */
	public static byte[] loadData(String url) {
		InputStream is = null;
		byte[] data = null;
		ByteArrayOutputStream baos = null;

		try {
			is = new URL(url).openStream();
			baos = new ByteArrayOutputStream();
			int ch = 0;
			while ((ch = is.read()) != -1) {
				baos.write(ch);
			}
			data = baos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
				baos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return data;
	}
}
