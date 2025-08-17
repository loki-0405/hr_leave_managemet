package com.example.HR_Leave_Management.DTO;


import com.example.HR_Leave_Management.Entity.RoleCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponse {
    private String token;
    private String email;
    private RoleCategory role;

}
