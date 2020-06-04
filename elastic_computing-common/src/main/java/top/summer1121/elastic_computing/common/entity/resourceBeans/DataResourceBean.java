package top.summer1121.elastic_computing.common.entity.resourceBeans;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import top.summer1121.elastic_computing.common.config.BaseConfig;
import top.summer1121.elastic_computing.common.util.AliYunOssUtil;
import top.summer1121.elastic_computing.common.util.FileUtil;
import top.summer1121.elastic_computing.common.util.ParamUtil;
import top.summer1121.elastic_computing.common.util.SpringUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * 描述：用于标识数据资源
 *
 * @author xtysummer1121@foxmail.com
 * @date 2020/5/25
 */
@Slf4j
public class DataResourceBean extends ResourceBean {
	@Override
	public String getFileUrl() throws Exception {
		if (!ParamUtil.isNotBlank(sign)) return null;

		StringBuilder sb = new StringBuilder(64);
		BaseConfig baseConfig = (BaseConfig) SpringUtil.getBean("BaseConfig");
		sb.append(baseConfig.getDataStoreUrlPrefix());
		sb.append(this.sign);
		sb.append(".json");
		return sb.toString();
	}

	@Override
	public String getFilePath() throws Exception {
		if (!ParamUtil.isNotBlank(sign)) return null;

		StringBuilder sb = new StringBuilder(64);
		BaseConfig baseConfig = (BaseConfig) SpringUtil.getBean("BaseConfig");
		sb.append(baseConfig.getDataCachePath());
		sb.append(this.sign);
		sb.append(".json");
		return sb.toString();
	}

	public static DataResourceBean createResourceBean(File file) {
		try {
			if (!file.exists() || file.length() <= 0) return null;

			InputStream inputStream = new FileInputStream(file);
			DataResourceBean resource = new DataResourceBean();
			String sign = DigestUtils.md5DigestAsHex(inputStream);

			//获取去除后缀的文件名
			String name = file.getName().replaceAll("[.][^.]+$", "");

			resource.setName(name).setSign(sign);

			String path = resource.getFileUrl();
			//如果本文件在oss中没有副本存在，上传本文件
			if (!FileUtil.findResource(path)) {
				path = "data/" + sign + ".json";
				inputStream = new FileInputStream(file);
				((AliYunOssUtil) SpringUtil.getBean("AliYunOssUtil")).upload(inputStream, path);
			}

			return resource;
		} catch (Exception e) {
			log.error("data上传失败", e);
			return null;
		}
	}
}
