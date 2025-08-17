package com.example.HR_Leave_Management.Service;

import com.example.HR_Leave_Management.DTO.HrDashboardDto;
import com.example.HR_Leave_Management.DTO.LeaveResponseDto;
import com.example.HR_Leave_Management.DTO.UserLeaveDetailsDto;
import com.example.HR_Leave_Management.Entity.LeaveRequest;
import com.example.HR_Leave_Management.Entity.LeaveStatus;
import com.example.HR_Leave_Management.Entity.User;
import com.example.HR_Leave_Management.Entity.UserLeaveDetails;
import com.example.HR_Leave_Management.Repository.LeaveRequestRepo;
import com.example.HR_Leave_Management.Repository.UserLeaveDetailsRepo;
import com.example.HR_Leave_Management.Repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@RequiredArgsConstructor
public class HrService {

    private final LeaveRequestRepo leaveRequestRepo;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserLeaveDetailsRepo userLeaveDetailsRepo;


    public String updateEmployee(Long id, User updatedUser) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found with ID: " + id));


        existingUser.setEmpName(updatedUser.getEmpName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setJoiningDate(updatedUser.getJoiningDate());
        existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
        existingUser.setAddress(updatedUser.getAddress());
        existingUser.setWorkStation(updatedUser.getWorkStation());
        existingUser.setDOB(updatedUser.getDOB());


        if (updatedUser.getRoleCategory() != null) {
            existingUser.setRoleCategory(updatedUser.getRoleCategory());
        }
        if (updatedUser.getEmpType() != null) {
            existingUser.setEmpType(updatedUser.getEmpType());
        }
        if (updatedUser.getDepartment() != null) {
            existingUser.setDepartment(updatedUser.getDepartment());
        }
        if (updatedUser.getGender() != null) {
            existingUser.setGender(updatedUser.getGender());
        }
        if (updatedUser.getStatus() != null) {
            existingUser.setStatus(updatedUser.getStatus());
        }


        if (updatedUser.getPassword() != null && !updatedUser.getPassword().trim().isEmpty()) {
            String encodedPassword = passwordEncoder.encode(updatedUser.getPassword());
            existingUser.setPassword(encodedPassword);
            System.out.println("Password updated for user: " + existingUser.getEmpName());
        }

        User savedUser = userRepository.save(existingUser);
        System.out.println("Employee updated successfully with ID: " + savedUser.getId());

        return "Employee updated successfully";
    }

    public String partialUpdateEmployee(Long id, User partialUser) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found with ID: " + id));

        System.out.println("Partially updating employee with ID: " + id);


        if (partialUser.getEmpName() != null) {
            existingUser.setEmpName(partialUser.getEmpName());
        }
        if (partialUser.getEmail() != null) {

            Optional<User> userWithEmail = userRepository.findByEmail(partialUser.getEmail());
            if (userWithEmail.isPresent() && !userWithEmail.get().getId().equals(id)) {
                throw new IllegalArgumentException("Email already exists for another employee");
            }
            existingUser.setEmail(partialUser.getEmail());
        }
        if (partialUser.getJoiningDate() != null) {
            existingUser.setJoiningDate(partialUser.getJoiningDate());
        }
        if (partialUser.getPhoneNumber() != null) {
            existingUser.setPhoneNumber(partialUser.getPhoneNumber());
        }
        if (partialUser.getAddress() != null) {
            existingUser.setAddress(partialUser.getAddress());
        }
        if (partialUser.getWorkStation() != null) {
            existingUser.setWorkStation(partialUser.getWorkStation());
        }
        if (partialUser.getDOB() != null) {
            existingUser.setDOB(partialUser.getDOB());
        }


        if (partialUser.getRoleCategory() != null) {
            existingUser.setRoleCategory(partialUser.getRoleCategory());
        }
        if (partialUser.getEmpType() != null) {
            existingUser.setEmpType(partialUser.getEmpType());
        }
        if (partialUser.getDepartment() != null) {
            existingUser.setDepartment(partialUser.getDepartment());
        }
        if (partialUser.getGender() != null) {
            existingUser.setGender(partialUser.getGender());
        }
        if (partialUser.getStatus() != null) {
            existingUser.setStatus(partialUser.getStatus());
        }

        if (partialUser.getPassword() != null && !partialUser.getPassword().trim().isEmpty()) {
            String encodedPassword = passwordEncoder.encode(partialUser.getPassword());
            existingUser.setPassword(encodedPassword);
        }

        User savedUser = userRepository.save(existingUser);
        return "Employee updated successfully";
    }

    public String deleteEmployee(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("Employee not found with ID: " + userId);
        }

        userRepository.deleteById(userId);
        return "Employee deleted successfully";
    }

    public User getEmployeeById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found with ID: " + id));
    }

    public List<User> getAllEmployees() {
        return userRepository.findAll();
    }

    // Additional helper methods
    public List<User> getEmployeesByDepartment(String department) {
        // You can add a custom query method in UserRepository
        return userRepository.findAll().stream()
                .filter(user -> user.getDepartment() != null &&
                        user.getDepartment().name().equalsIgnoreCase(department))
                .toList();
    }

    public List<User> getEmployeesByStatus(String status) {
        return userRepository.findAll().stream()
                .filter(user -> user.getStatus() != null &&
                        user.getStatus().name().equalsIgnoreCase(status))
                .toList();
    }

    public void AddEmpLeaveDetails(UserLeaveDetailsDto userLeaveDetailsDto,Long id) {
        UserLeaveDetails userLeaveDetails = new UserLeaveDetails();
        userLeaveDetails.setBalanceLeaves(userLeaveDetailsDto.getBalanceLeaves());
        userLeaveDetails.setLeavesTaken(userLeaveDetailsDto.getLeavesTaken());
        userLeaveDetails.setMaxNoLeaves(userLeaveDetailsDto.getMaxNoLeaves());
        userLeaveDetails.setLeavesPerYear(userLeaveDetailsDto.getLeavesPerYear());
        System.out.println(userLeaveDetailsDto.getBalanceLeaves()+" "+userLeaveDetailsDto.getMaxNoLeaves());
        User user = userRepository.findById(id).orElseThrow();
        userLeaveDetails.setUser(user);

         userLeaveDetailsRepo.save(userLeaveDetails);
    }

    @Transactional
    public void DelEmpLeaveDetails(Long userId) {
        userLeaveDetailsRepo.deleteByUserId(userId);
    }

    public void UpdEmpLeaveDetails(UserLeaveDetailsDto userLeaveDetailsDto,Long id) {
        UserLeaveDetails userLeaveDetails = userLeaveDetailsRepo.findByUserId(id);
        userLeaveDetails.setLeavesPerYear(userLeaveDetailsDto.getLeavesPerYear());
        userLeaveDetails.setBalanceLeaves(userLeaveDetailsDto.getBalanceLeaves());
        userLeaveDetails.setLeavesTaken(userLeaveDetailsDto.getLeavesTaken());
        userLeaveDetails.setMaxNoLeaves(userLeaveDetailsDto.getMaxNoLeaves());

        userLeaveDetailsRepo.save(userLeaveDetails);
    }

    public void approveLeave(Long leaveId) {
        LeaveRequest request = leaveRequestRepo.findById(leaveId)
                .orElseThrow(() -> new RuntimeException("Leave request not found with ID: " + leaveId));

        if (request.getLeaveStatus() != LeaveStatus.PENDING) {
            throw new IllegalStateException("Only pending requests can be approved");
        }

        LocalDate startDate = request.getStartDate().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        LocalDate endDate = request.getEndDate().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        long diff = ChronoUnit.DAYS.between(startDate, endDate) + 1;
        if (diff <= 0) {
            throw new IllegalArgumentException("End date must be after start date");
        }

        UserLeaveDetails leaveDetails = userLeaveDetailsRepo.findByUserId(request.getUser().getId());
        if (leaveDetails == null) {
            throw new RuntimeException("Leave details not found for user ID: " + request.getUser().getId());
        }

        int updatedLeavesTaken = leaveDetails.getLeavesTaken() + (int) diff;
        int updatedBalanceLeaves = leaveDetails.getBalanceLeaves() - (int) diff;

        if (updatedBalanceLeaves < 0) {
            throw new IllegalStateException("Not enough balance leaves to approve this request");
        }

        leaveDetails.setLeavesTaken(updatedLeavesTaken);
        leaveDetails.setBalanceLeaves(updatedBalanceLeaves);

        request.setLeaveStatus(LeaveStatus.APPROVED);
        leaveRequestRepo.save(request);
        userLeaveDetailsRepo.save(leaveDetails);
    }


    public void rejectLeave(Long leaveId) {

        LeaveRequest request = leaveRequestRepo.findById(leaveId)
                .orElseThrow(() -> new RuntimeException("Leave request not found with ID: " + leaveId));

        if (request.getLeaveStatus() != LeaveStatus.PENDING) {
            throw new IllegalStateException("Only pending requests can be rejected");
        }

        LocalDate startDate = request.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endDate = request.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        List<LeaveRequest> approvedLeaves = leaveRequestRepo
                .findAllByUserIdAndLeaveStatus(request.getUser().getId(), LeaveStatus.APPROVED);

        boolean overlap = approvedLeaves.stream().anyMatch(leave -> {
            LocalDate approvedStart = leave.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate approvedEnd = leave.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            return !(endDate.isBefore(approvedStart) || startDate.isAfter(approvedEnd));
        });

        if (overlap) {
            request.setLeaveStatus(LeaveStatus.REJECTED);
            leaveRequestRepo.save(request);
        }
    }

    public HrDashboardDto getHrDashboard(Long hrId) {
        HrDashboardDto dto = new HrDashboardDto();

        List<LeaveResponseDto> leaveResponseDtos = new ArrayList<LeaveResponseDto>();

        List<LeaveRequest> leaveRequests = leaveRequestRepo.findAllByManagerId(hrId);

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

        dto.setTotalRequests((long) leaveRequests.size());
        dto.setApprovedRequests(approved);
        dto.setRejectedRequests(rejected);
        dto.setPendingRequests(pending);
        dto.setLeaveResponseDtos(leaveResponseDtos);

        return dto;
    }


}