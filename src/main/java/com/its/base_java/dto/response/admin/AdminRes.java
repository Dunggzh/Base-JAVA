package com.its.base_java.dto.response.admin;

import com.its.base_java.entities.admin.constant.AdminStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
public class AdminRes {
    private int id;
    private String name;
    private String email;
    private AdminStatus status;
    private Integer roleId;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

}
