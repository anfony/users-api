package com.anfony.usersapi.dto;

import com.anfony.usersapi.model.Address;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class UserResponse {
    private UUID id;
    private String email;
    private String name;
    private String phone;
    private String taxId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime createdAt;

    private List<Address> addresses;
}
