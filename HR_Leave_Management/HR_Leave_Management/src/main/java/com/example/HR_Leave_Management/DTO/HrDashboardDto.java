package com.example.HR_Leave_Management.DTO;

import com.example.HR_Leave_Management.Entity.LeaveRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HrDashboardDto {

        private Long totalRequests;
        private Long approvedRequests;
        private Long rejectedRequests;
        private Long pendingRequests;
        private List<LeaveResponseDto> leaveResponseDtos;
}


