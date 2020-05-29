package top.summer1121.elastic_computing.common.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @description: 阿里云oss配置文件
 * @author: mengwenyi
 * @date: 2019/3/13 10:42
 */
@PropertySource("classpath:/config/oss.config.properties")
@Component
@Getter
public class OssPropResource {

    @Value("${user.storage.oss.end.point}")
    private String userStorageOssEndpoint;

    @Value("${user.storage.oss.access.key}")
    private String userStorageOssAccessKey;

    @Value("${user.storage.oss.access.secret}")
    private String userStorageOssAccessSecret;

    @Value("${user.storage.oss.bucket.name}")
    private String userStorageOssBucketName;

    @Value("${user.storage.oss.region}")
    private String userStorageOssRegion;

    @Value("${user.storage.oss.sts.api.version}")
    private String userStorageOssStsApiVersion;

    @Value("${user.storage.oss.role.arn}")
    private String userStorageOssRoleArn;


    @Value("${user.storage.oss.token.expire.time}")
    private String userStorageOssTokenExpireTime;

    @Value("${user.storage.oss.token.policy}")
    private String userStorageOssTokenPolicy;

//    @Value("${user.storage.oss.cdn.domain}")
//    private String userStorageOssCdnDomain;
}