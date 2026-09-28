package com.its.base_java.entities.upload_file;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.its.base_java.entities.BaseEntity;
import com.its.base_java.entities.upload_file.constant.UploadFileType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "upload_files")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UploadFile extends BaseEntity {

    private String originFilePath;
    private String thumbFilePath;
    private String originUrl;
    private String thumbUrl;
    @Column(columnDefinition = "int")
    private UploadFileType type;
    private Integer width;
    private Integer height;
    private Integer duration;
    private Long size;

}
