package top.summer1121.elastic_computing.common.entity.resourceBeans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * @author xtysummer1121@foxmail.com
 * @description 资源包装类
 * @date 2020/4/27
 */
@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public abstract class ResourceBean {
	/**
	 * 访问路径
	 */
	String url;
	/**
	 * 签名
	 */
	String sign;

	/**
	 * 资源名称
	 */
	String name;

	/**
	 * 根据sign获取远程访问路径
	 *
	 * @return {@link String}
	 * @className ResourceBean
	 * @author xtysummer1121@foxmail.com
	 * @methodName getFileUrl
	 * @date 2020/5/6
	 */
	abstract public String getFileUrl() throws Exception;


	/**
	 * 根据sign获取本地访问路径
	 *
	 * @return {@link String}
	 * @className ResourceBean
	 * @author xtysummer1121@foxmail.com
	 * @methodName getFileUrl
	 * @date 2020/5/6
	 */
	abstract public String getFilePath() throws Exception;
}
