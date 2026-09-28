package com.its.base_java.dto.request.admin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChangePasswordReq {
    @NotBlank
    String oldPassword;
    @NotBlank
    @Size(min = 6, message = "{password_min_characters}")
    String newPassword;
    @NotBlank
    String confirmPassword;

    @JsonIgnore
    @AssertTrue(message = "Password don't matching")
    public boolean isValid() {
        return newPassword.equals(confirmPassword);
    }
}