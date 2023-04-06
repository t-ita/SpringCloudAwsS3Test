package com.example.springcloudawss3test;

import io.awspring.cloud.s3.S3PathMatchingResourcePatternResolver;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.ResourcePatternResolver;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class ResourcePatternResolverConfig {

    @Bean
    @Primary
    public ResourcePatternResolver setupResolver(S3Client s3Client, ApplicationContext applicationContext) {
        return new S3PathMatchingResourcePatternResolver(s3Client, applicationContext);
    }

}
