package top.summer1121.elastic_computing.common.util;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.ListObjectsRequest;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.aliyun.oss.model.PutObjectResult;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.http.ProtocolType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.sts.model.v20150401.AssumeRoleRequest;
import com.aliyuncs.sts.model.v20150401.AssumeRoleResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.summer1121.elastic_computing.common.config.OssPropResource;

import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Description
 * @Author: WuPengFei
 * @Date: 2019/8/7 14:31
 */
@Component("AliYunOssUtil")
@Slf4j
public class AliYunOssUtil {
	@Autowired
	private OssPropResource ossPropResource;

	public AssumeRoleResponse assumeRole(String accessKeyId, String accessKeySecret,
	                                     String roleArn, String roleSessionName, String policy,
	                                     ProtocolType protocolType,
	                                     long durationSeconds) throws ClientException,
			com.aliyuncs.exceptions.ClientException {

		try {
			// 创建一个 Aliyun Acs Client, 用于发起 OpenAPI 请求
			IClientProfile profile = DefaultProfile.getProfile(ossPropResource.getUserStorageOssRegion(), accessKeyId,
					accessKeySecret);
			DefaultAcsClient client = new DefaultAcsClient(profile);

			// 创建一个 AssumeRoleRequest 并设置请求参数
			final AssumeRoleRequest request = new AssumeRoleRequest();
			request.setVersion(ossPropResource.getUserStorageOssStsApiVersion());
			request.setMethod(MethodType.POST);
			request.setProtocol(protocolType);

			request.setRoleArn(roleArn);
			request.setRoleSessionName(roleSessionName);
			request.setPolicy(policy);
			request.setDurationSeconds(durationSeconds);

			// 发起请求，并得到response
			final AssumeRoleResponse response = client.getAcsResponse(request);

			return response;
		} catch (Exception e) {
			throw e;
		}

	}

//	public ResponseBody getToken() throws com.aliyuncs.exceptions.ClientException {
//		ResponseBody result = new ResponseBody();
//		Map<String, Object> respMap = new LinkedHashMap<>();
//		// RoleSessionName 是临时Token的会话名称，自己指定用于标识你的用户，主要用于审计，或者用于区分Token颁发给谁
//		// 但是注意RoleSessionName的长度和规则，不要有空格，只能有'-' '_' 字母和数字等字符
//		// 具体规则请参考API文档中的格式要求
//		String roleSessionName = "prac";
//		// 此处必须为 HTTPS
//		ProtocolType protocolType = ProtocolType.HTTPS;
//		try {
//			final AssumeRoleResponse stsResponse =
//					assumeRole(ossPropResource.getUserStorageOssAccessKey(),
//							ossPropResource.getUserStorageOssAccessSecret(),
//							ossPropResource.getUserStorageOssRoleArn(), roleSessionName,
//							ossPropResource.getUserStorageOssTokenPolicy(), protocolType,
//							Long.valueOf(ossPropResource.getUserStorageOssTokenExpireTime()));
//			respMap.put("AccessKeyId", stsResponse.getCredentials().getAccessKeyId());
//			respMap.put("AccessKeySecret", stsResponse.getCredentials().getAccessKeySecret());
//			respMap.put("stsToken", stsResponse.getCredentials().getSecurityToken());
//			respMap.put("Expiration", stsResponse.getCredentials().getExpiration());
//			respMap.put("region", ossPropResource.getUserStorageOssRegion());
//			respMap.put("bucket", ossPropResource.getUserStorageOssBucketName());
//			respMap.put("cname", true);
//			respMap.put("endPoint",ossPropResource.getUserStorageOssEndpoint().replace("http://",""));
////			String endPoint = ossPropResource.getUserStorageOssCdnDomain();
////			endPoint = endPoint.replace("https://", "");
////			respMap.put("endPoint", endPoint);
//			result.setSuccess(true);
//			result.setMessage("获取成功");
//			result.setData(respMap);
//			result.setCode(HttpStatus.OK.value());
//		} catch (ClientException e) {
//			e.printStackTrace();
//			result.setSuccess(false);
//			result.setMessage(e.getMessage());
//		}
//		return result;
//	}


	public void upload(InputStream inputStream, String objectName) throws Exception {
		// Endpoint以杭州为例，其它Region请按实际情况填写。
		String endpoint = ossPropResource.getUserStorageOssEndpoint();
		// 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
		String accessKeyId = ossPropResource.getUserStorageOssAccessKey();
		String accessKeySecret = ossPropResource.getUserStorageOssAccessSecret();
		// 创建OSSClient实例。
		OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
		// 上传网络流。
		PutObjectResult result = ossClient.putObject(ossPropResource.getUserStorageOssBucketName(), objectName, inputStream);
		// 关闭OSSClient。
		ossClient.shutdown();
	}

//	public ResponseBody testToken(String token, String accessKey, String accessSecret) {
//		OSS ossClient = null;
//		try {
//			// 使用教研云cdn
//			String endpoint = ossPropResource.getUserStorageOssEndpoint();
//			// 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
//			String accessKeyId = accessKey;
//			String accessKeySecret = accessSecret;
//			String securityToken = token;
//
//			// 用户拿到STS临时凭证后，通过其中的安全令牌（SecurityToken）和临时访问密钥（AccessKeyId和AccessKeySecret）生成OSSClient。
//			// 创建OSSClient实例。
//			ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret, securityToken);
//			// 构造ListObjectsRequest请求。
//			ListObjectsRequest listObjectsRequest =
//					new ListObjectsRequest(ossPropResource.getUserStorageOssBucketName());
//			// 设置prefix参数来获取feedback目录下的所有文件。
//			listObjectsRequest.setPrefix("feedback/");
//
//			// 递归列出feedback目录下的所有文件。
//			ObjectListing listing = ossClient.listObjects(listObjectsRequest);
//
//			StringBuilder sb = new StringBuilder();
//			// 遍历所有文件。
//			sb.append("Objects:");
//			for (OSSObjectSummary objectSummary : listing.getObjectSummaries()) {
//				sb.append(objectSummary.getKey());
//				sb.append("\n");
//			}
//
//			// 遍历所有commonPrefix。
//			sb.append("\nCommonPrefixes:");
//			for (String commonPrefix : listing.getCommonPrefixes()) {
//				sb.append(commonPrefix);
//				sb.append("\n");
//			}
//
//			return new ResponseBody().success(sb.toString());
//		} catch (OSSException e) {
//			log.error(e.getMessage(), e);
//			return new ResponseBody().error(e.getMessage());
//		} finally {
//			// 关闭OSSClient。
//			if (ossClient != null)
//				ossClient.shutdown();
//		}
//	}
}
