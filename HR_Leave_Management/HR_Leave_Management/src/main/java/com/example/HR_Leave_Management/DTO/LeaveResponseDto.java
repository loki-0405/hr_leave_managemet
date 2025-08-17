package com.example.HR_Leave_Management.DTO;

import com.example.HR_Leave_Management.Entity.LeaveStatus;
import com.example.HR_Leave_Management.Entity.LeaveType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LeaveResponseDto {

    private Long requestid;
    private Date StartDate;
    private Date EndDate;
    private LeaveType leaveType;
    private String Reason;

    private Long managerId;

    @Enumerated(EnumType.STRING)
    private LeaveStatus leaveStatus;

    private Long userid;
}
