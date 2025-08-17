package com.example.HR_Leave_Management.Repository;

import com.example.HR_Leave_Management.Entity.LeaveRequest;
import com.example.HR_Leave_Management.Entity.LeaveStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaveRequestRepo extends JpaRepository<LeaveRequest,Long> {

    List<LeaveRequest> findAllByManagerId(Long hrId);

    List<LeaveRequest> findAllByUserId(Long empId);

    List<LeaveRequest> findAllByUserIdAndLeaveStatus(Long id, LeaveStatus leaveStatus);
}
