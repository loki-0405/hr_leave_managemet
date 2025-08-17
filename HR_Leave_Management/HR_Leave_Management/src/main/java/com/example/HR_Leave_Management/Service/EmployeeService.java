package com.example.HR_Leave_Management.Service;


import com.example.HR_Leave_Management.DTO.EmpDashBoardDto;
import com.example.HR_Leave_Management.DTO.HrDashboardDto;
import com.example.HR_Leave_Management.DTO.LeaveRequestDto;
import com.example.HR_Leave_Management.DTO.LeaveResponseDto;
import com.example.HR_Leave_Management.Entity.LeaveRequest;
import com.example.HR_Leave_Management.Entity.LeaveStatus;
import com.example.HR_Leave_Management.Entity.User;
import com.example.HR_Leave_Management.Repository.LeaveRequestRepo;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Builder
public class EmployeeService {

    private final LeaveRequestRepo leaveRequestRepo;

    public void leaveRequest(LeaveRequestDto leaveRequestdto, User user) {
        LeaveRequest leaveRequest = new LeaveRequest();
        leaveRequest.setEndDate(leaveRequestdto.getEndDate());
        leaveRequest.setLeaveType(leaveRequestdto.getLeaveType());
        leaveRequest.setUser(user);
        leaveRequest.setManagerId(user.getManagerId());
        leaveRequest.setLeaveStatus(LeaveStatus.PENDING);
        leaveRequest.setReason(leaveRequestdto.getReason());
        leaveRequest.setStartDate(leaveRequestdto.getStartDate());

        leaveRequestRepo.save(leaveRequest);
    }

    public void updateLeaveRequest(LeaveRequestDto leaveRequestDto, Long id) {

        LeaveRequest leaveRequest = leaveRequestRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave request not found with id: " + id));

        leaveRequest.setStartDate(leaveRequestDto.getStartDate());
        leaveRequest.setEndDate(leaveRequestDto.getEndDate());
        leaveRequest.setLeaveType(leaveRequestDto.getLeaveType());
        leaveRequest.setReason(leaveRequestDto.getReason());

        leaveRequestRepo.save(leaveRequest);

    }

    public void DeleteEmpLeaveRequest(Long id) {
        leaveRequestRepo.deleteById(id);
    }


    public EmpDashBoardDto getEmployeeDashboard(Long empId) {

        EmpDashBoardDto empDashBoardDto = new EmpDashBoardDto();
        List<LeaveResponseDto> leaveResponseDtos = new ArrayList<LeaveResponseDto>();

        List<LeaveRequest> leaveRequests = leaveRequestRepo.findAllByUserId(empId);

        long approved = 0;
        long rejected = 0;
        long pending = 0;

        for (LeaveRequest lr : leaveRequests) {
            if (lr.getLeaveStatus() == LeaveStatus.APPROVED) {
                approved++;
            } else if (lr.getLeaveStatus() == LeaveStatus.REJECTED) {
                rejected++;
            } else if (lr.getLeaveStatus() == LeaveStatus.PENDING) {
                pending++;
            }
            LeaveResponseDto leaveResponseDto = new LeaveResponseDto();

            leaveResponseDto.setLeaveStatus(lr.getLeaveStatus());
            leaveResponseDto.setLeaveType(lr.getLeaveType());
            leaveResponseDto.setReason(lr.getReason());
            leaveResponseDto.setEndDate(lr.getEndDate());
            leaveResponseDto.setManagerId(lr.getManagerId());
            leaveResponseDto.setStartDate(lr.getStartDate());
            leaveResponseDto.setUserid(lr.getUser().getId());
            leaveResponseDto.setRequestid(lr.getId());

            leaveResponseDtos.add(leaveResponseDto);
        }

        empDashBoardDto.setTotalRequests((long) leaveRequests.size());
        empDashBoardDto.setApprovedRequests(approved);
        empDashBoardDto.setRejectedRequests(rejected);
        empDashBoardDto.setPendingRequests(pending);
        empDashBoardDto.setLeaveResponseDtos(leaveResponseDtos);

        return empDashBoardDto;
    }
}
