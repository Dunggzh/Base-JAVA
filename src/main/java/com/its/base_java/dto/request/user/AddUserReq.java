package com.its.base_java.dto.request.user;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddUserReq {
    @NotNull
    int customerId;
    @NotBlank
    String name;
    String phone;
    @NotBlank
    String email;
    @NotBlank
    String password;
    @NotBlank
    String confirmPassword;

    Integer roleId;

    @AssertTrue(message = "Password don't matching")
    public boolean isValid() {
        return password.equals(confirmPassword);
    }
}
