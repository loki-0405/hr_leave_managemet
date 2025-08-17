package com.example.HR_Leave_Management.DTO;

import com.example.HR_Leave_Management.Entity.LeaveType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LeaveRequestDto {

    private Date startDate;
    private Date endDate;
    private LeaveType leaveType;
    private String reason;
}