package top.summer1121.elastic_computing.common.util;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import top.summer1121.elastic_computing.common.customException.ApplicationException;
import top.summer1121.elastic_computing.common.customException.ExceptionContent;
import top.summer1121.elastic_computing.common.entity.resourceBeans.DataResourceBean;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 描述：文件工具
 *
 * @author xtysummer1121@foxmail.com
 * @date 2020/5/16
 */
@Slf4j
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
			if (is == null) return null;

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

	/**
	 * 清除本地缓存中较久的数据(删除指定目录下，maxCount以外，最老的文件)
	 *
	 * @param path
	 * @return
	 * @className FileUtil
	 * @author xtysummer1121@foxmail.com
	 * @date 2020/5/24
	 */
	public static void clearLocalDir(String path, Integer maxCount) {
		File file = new File(path);
		try {

			List<File> fileList = new ArrayList<>();
			//如果还没创建或者路径不存在.或者为空，返回
			if (file == null || !file.exists() || file.length() == 0)
				return;
			fileList.addAll(Arrays.asList(file.listFiles()));
			if (fileList.size() <= maxCount)
				return;
			List<File> deleteFiles = fileList.stream()
					.sorted(new Comparator<File>() {
						@Override
						public int compare(File o1, File o2) {
							return Long.compare(o1.lastModified(), o2.lastModified());
						}
					})
					.collect(Collectors.toList())
					.subList(maxCount, fileList.size());
			deleteFiles.stream().forEach((entity) -> entity.delete());

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * 根据bytes[]，缓存文件到本地
	 *
	 * @param filePath    存储目录（不包含文件名）
	 * @param bytes       要存入的字节码
	 * @param fileName    文件名称
	 * @param isOverWrite 是否覆盖
	 * @return
	 * @className FileUtil
	 * @author xtysummer1121@foxmail.com
	 * @date 2020/5/24
	 */
	public static void writeFileByBytes(byte[] bytes, String filePath, String fileName, boolean isOverWrite) {
		BufferedOutputStream bos = null;
		FileOutputStream fos = null;
		File file = null;
		try {
			// 判断文件目录是否存在,不存在则创建目录
			File dir = new File(filePath);
			if (!dir.exists()) {
				dir.mkdirs();
			}

			file = new File(filePath + "\\" + fileName);
			//如果文件已经存在，并且不覆盖，则不做操作
			if (file.exists() && isOverWrite == false) {
				return;
			}

			//输出流
			fos = new FileOutputStream(file);

			//缓冲流
			bos = new BufferedOutputStream(fos);

			//将字节数组写出
			bos.write(bytes);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 存储data数据到指定位置，并返回目标JSON对象
	 *
	 * @param dataResourceBean 要存入的数据资源
	 * @return
	 * @className FileUtil
	 * @author xtysummer1121@foxmail.com
	 * @date 2020/5/25
	 */
	public static JSONObject loadAndCacheData(DataResourceBean dataResourceBean) throws Exception {

		File file = new File(dataResourceBean.getFilePath());

		//如果本文件不存在
		if (!file.exists()) {
			//从oss读取文件
			byte[] data = FileUtil.loadData(dataResourceBean.getFileUrl());
			//做本地缓存
			writeFileByBytes(data, file.getPath(), file.getName(), false);
		}
		if (!file.exists() || file.length() == 0) {
			log.error("拉取数据文件失败");
			throw new ApplicationException(ExceptionContent.DataCenterNetException);
		}

		return readFileToJson(file);
	}

	/**
	 * 将数据文件读为json文件
	 *
	 * @author xtysummer1121@foxmail.com
	 * @date 2020/5/29
	 * @param file
	 * @return {@link JSONObject}
	 */
	public static JSONObject readFileToJson(File file) throws ApplicationException {
		StringBuilder sb = null;
		try {
			if (file == null || file.length() <= 0) return new JSONObject();
			FileInputStream in = new FileInputStream(file);
			InputStreamReader streamReader = new InputStreamReader(in);
			BufferedReader reader = new BufferedReader(streamReader);
			String line;
			sb = new StringBuilder();

			while ((line = reader.readLine()) != null) {
				sb.append(line);
				line = null;
			}
		} catch (Exception e) {
			log.error("读取数据异常", e);
		}

		try {
			return (JSONObject) JSON.parse(sb.toString());
		} catch (Exception e) {
			log.error("json转换数据文件格式", e);
			throw new ApplicationException(ExceptionContent.JsonFormatException);
		}
	}

	/**
	 * 定位某个资源文件是否存在
	 *
	 * @author xtysummer1121@foxmail.com
	 * @date 2020/5/29
	 * @param urlName 
	 * @return {@link boolean}
	 */
	public static boolean  findResource(String urlName){

		try {
			URL url = new URL(urlName);
			// 返回一个 URLConnection 对象，它表示到 URL 所引用的远程对象的连接。
			URLConnection uc = url.openConnection();
			// 打开的连接读取的输入流。
			InputStream in = uc.getInputStream();
			return  true;
		} catch (Exception e) {
			return false;
		}
	}
}
