package com.example.HR_Leave_Management.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmpDashBoardDto {

    private Long totalRequests;
    private Long approvedRequests;
    private Long rejectedRequests;
    private Long pendingRequests;
    private List<LeaveResponseDto> leaveResponseDtos;
}
