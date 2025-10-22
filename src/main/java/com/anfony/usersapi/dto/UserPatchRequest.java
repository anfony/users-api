package com.anfony.usersapi.dto;

import com.anfony.usersapi.model.Address;
import lombok.Data;

import java.util.List;

@Data
public class UserPatchRequest {
    private String email;
    private String name;
    private String phone;
    private String password;
    private String taxId;
    private List<Address> addresses;
}
