package com.aptechph.financial_dashboard.dto;

import lombok.Data;

@Data
public class UserDto {

    private Long id;
    private String username;
    private String email;
    private String role;
    private Boolean isEmailVerified;
    private Boolean isAccountActive;
}