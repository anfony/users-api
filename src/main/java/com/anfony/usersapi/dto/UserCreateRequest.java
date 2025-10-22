package com.anfony.usersapi.dto;

import com.anfony.usersapi.model.Address;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class UserCreateRequest {

    @NotBlank @Email
    private String email;

    @NotBlank
    private String name;

    @NotBlank
    private String phone;

    @NotBlank
    private String password;

    @NotBlank
    private String taxId;

    private List<Address> addresses;
}
