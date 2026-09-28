package com.its.base_java.controller;

import com.its.base_java.component.Translator;
import com.its.base_java.dto.response.BaseResponse;
import com.its.base_java.entities.upload_file.UploadFile;
import com.its.base_java.exceptions.BusinessException;
import com.its.base_java.services.file.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/")
public class FileController {

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping("api/v1/media/upload-image")
    public ResponseEntity<BaseResponse<UploadFile>> uploadImage(@RequestParam("file") final MultipartFile file) {
        if (file == null) {
            throw new BusinessException(Translator.toLocale("required_fields"));
        }
        if (file.getSize() > 1024 * 1024 * 20) {
            throw new BusinessException("File size is too large, please choose file smaller than 20MB");
        }
        UploadFile uploadFile = fileStorageService.storeImage(file);
        return ResponseEntity.ok(new BaseResponse<>(uploadFile));
    }

    @GetMapping("image/{fileName:.+}")
    public ResponseEntity<InputStreamResource> getImage(@PathVariable final String fileName) throws Exception {
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG)
                .body(new InputStreamResource(fileStorageService.getInputStream("image/" + fileName)));
    }

}

