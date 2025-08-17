package com.example.HR_Leave_Management.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserLeaveReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate startDate;
    private LocalDate endDate;

    private Integer totalLeaveBalance;
    private Integer totalLeavesTaken;
    private Integer totalLeavesApproved;
    private Integer totalLeavesRejected;

    private Integer sickLeavesTaken;
    private Integer casualLeavesTaken;
    private Integer annualLeavesTaken;
    private Integer unpaidLeavesTaken;

    private Double totalLeaveSalaryDeduction;
    private String reportStatus;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}