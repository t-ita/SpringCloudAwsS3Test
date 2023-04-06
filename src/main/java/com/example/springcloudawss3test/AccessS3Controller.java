package com.example.springcloudawss3test;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AccessS3Controller {
    private final ResourceLoader resourceLoader;
    private final ResourcePatternResolver resourcePatternResolver;

    @Value("s3://spring.cloud.aws.test/subfoder1/sample4.txt")
    private Resource s3Resource;

    public AccessS3Controller(ResourceLoader resourceLoader,
                              ResourcePatternResolver resourcePatternResolver) {
        this.resourceLoader = resourceLoader;
        this.resourcePatternResolver = resourcePatternResolver;
    }

    @GetMapping("/show-s3-1")
    public String showS3ResourceWithLoader() throws IOException {
        var resource = resourceLoader.getResource("s3://spring.cloud.aws.test/sample3.txt");
        return showContentsOf(resource);
    }

    @GetMapping("/show-s3-2")
    public String showS3Resource() throws IOException {
        return showContentsOf(s3Resource);
    }

    @GetMapping("/search-s3")
    public List<String> searchS3Resources() throws IOException {
        var resources = resourcePatternResolver.getResources("s3://spring.cloud.aws.test/**/*");
        return Arrays.stream(resources)
                .map(resource -> {
                    try {
                        var filename = resource.getFilename();
                        return showContentsOf(resource);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
    }

    /**
     * Resource の中身を表示するユーティリティクラス
     */
    String showContentsOf(Resource resource) throws IOException {
        try (var inputStream = resource.getInputStream();
             var inputStreamReader = new InputStreamReader(inputStream);
             var bufferedReader = new BufferedReader(inputStreamReader)) {
            return bufferedReader.lines().collect(Collectors.joining("\n"));
        }
    }

}
