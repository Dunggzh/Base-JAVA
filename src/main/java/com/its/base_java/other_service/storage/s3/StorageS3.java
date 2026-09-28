package com.its.base_java.other_service.storage.s3;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.its.base_java.other_service.storage.StorageConfig;
import com.its.base_java.other_service.storage.StorageResource;
import lombok.extern.log4j.Log4j2;

import java.io.InputStream;

@Log4j2
public class StorageS3 implements StorageResource {

    private final StorageS3Config s3StorageConfig;

    private final StorageS3Helper s3StorageService;

    public StorageS3(StorageConfig s3StorageConfig) {
        log.info("configure: {}", s3StorageConfig);
        this.s3StorageConfig = (StorageS3Config) s3StorageConfig;
        this.s3StorageService = new StorageS3Helper();
    }

    private AmazonS3 createS3() {
        AWSCredentials credentials = new BasicAWSCredentials(this.s3StorageConfig.getAccessKey(), this.s3StorageConfig.getSecretKey());
        AmazonS3 s3 = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.valueOf(s3StorageConfig.getRegion()))
                .build();
        log.info("S3 Storage connected!");
        return s3;
    }


    @Override
    public InputStream readResource(String path) {
        log.info("s3 read File: {}", path);
        if (path.startsWith("/")) {
            path = path.substring(1);
        }
        return s3StorageService.readFile(createS3(), path, s3StorageConfig.getBucket());
    }

    @Override
    public String writeResource(InputStream inputStream, String path) {
        AmazonS3 s3Client = createS3();
        return s3StorageService.uploadFile(s3Client, inputStream, path, this.s3StorageConfig.getBucket());
    }

    @Override
    public boolean deleteFile(String file) {
        log.info("s3 delete File: {}", file);
        AmazonS3 amazonS3 = createS3();
        return s3StorageService.deleteFile(amazonS3, file, s3StorageConfig.getBucket());
    }

    @Override
    public String getUrl(String file) {
        return null;
    }
}
