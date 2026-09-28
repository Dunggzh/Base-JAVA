package com.its.base_java.repositories.media;


import com.its.base_java.entities.upload_file.UploadFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaRepository extends JpaRepository<UploadFile, Integer> {
    UploadFile findUploadFileById(int id);
}
