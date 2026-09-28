package com.its.base_java.services.file;

import com.its.base_java.entities.upload_file.UploadFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface FileStorageService {

    UploadFile storeImage(final MultipartFile file);
    void deleteFile(int fileId);
    InputStream getInputStream(final String fileName);
}
