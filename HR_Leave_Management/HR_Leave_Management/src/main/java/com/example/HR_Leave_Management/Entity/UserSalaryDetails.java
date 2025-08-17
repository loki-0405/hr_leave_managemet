package com.example.HR_Leave_Management.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserSalaryDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double basicSalary;
    private Double allowances;
    private Double bonus;
    private Double grossSalary;
    private Double deductions;
    private Double netSalary;
    private Double overtimeSalary;
    private String currency;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}