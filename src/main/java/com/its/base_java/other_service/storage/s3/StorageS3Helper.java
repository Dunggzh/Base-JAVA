package com.its.base_java.other_service.storage.s3;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import lombok.extern.log4j.Log4j2;

import java.io.InputStream;

@Log4j2
public class StorageS3Helper {
    public String uploadFile(AmazonS3 s3Client, InputStream inputStream, String path, String bucket) {
        String folder = path.substring(0, path.indexOf('/') + 1);
        String subFolder = path.substring(0, path.lastIndexOf('/') + 1);
        if (!isExists(folder, bucket, s3Client)) s3Client.putObject(bucket, folder, folder);
        if (!isExists(subFolder, bucket, s3Client)) s3Client.putObject(bucket, subFolder, subFolder);
        s3Client.putObject(bucket, path, inputStream, null);
        return path;
    }

    public InputStream readFile(AmazonS3 s3Client, String path, String bucket) {
        S3Object s3object = s3Client.getObject(bucket, path);
        return s3object.getObjectContent();
    }

    public boolean deleteFile(AmazonS3 s3Client, String path, String bucket) {
        try {
            DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucket, path);
            s3Client.deleteObject(deleteObjectRequest);
            return true;
        } catch (AmazonServiceException e) {
            log.info("can not delete by s3 storage because: " + e.getMessage());
            return false;
        }
    }

    public boolean isExists(String key, String bucketName, AmazonS3 s3Client) {
        try {
            s3Client.getObjectMetadata(bucketName, key);
            return true;
        } catch (AmazonServiceException var3) {
            return false;
        }
    }
}
