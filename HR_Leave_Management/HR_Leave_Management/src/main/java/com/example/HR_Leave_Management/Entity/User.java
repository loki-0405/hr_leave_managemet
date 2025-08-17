package com.example.HR_Leave_Management.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String EmpName;
    private Date JoiningDate;
    private Long PhoneNumber;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String Address;

    @Column(nullable = false)
    private Long ManagerId;

    @Enumerated(EnumType.STRING)
    private EmpType empType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleCategory roleCategory;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private Date DOB;
    private String WorkStation;

    @Enumerated(EnumType.STRING)
    private Department department;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserLeaveDetails userLeaveDetails;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserSalaryDetails userSalaryDetails;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<LeaveRequest> leaveRequestList;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserLeaveReport userLeaveReport;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (roleCategory == null) {
            return List.of(); // Return empty list if no role assigned
        }
        return List.of(new SimpleGrantedAuthority("ROLE_" + roleCategory.name()));
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Explicitly return true instead of calling super
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Explicitly return true instead of calling super
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Explicitly return true instead of calling super
    }

    @Override
    public boolean isEnabled() {
        return true; // Explicitly return true instead of calling super
    }
}