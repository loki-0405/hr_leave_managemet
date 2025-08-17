package com.example.HR_Leave_Management.Controller;

import com.example.HR_Leave_Management.DTO.EmpDashBoardDto;
import com.example.HR_Leave_Management.DTO.LeaveRequestDto;
import com.example.HR_Leave_Management.Entity.User;
import com.example.HR_Leave_Management.Service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8081")
public class EmployeeController {

      private final EmployeeService employeeService;

    @GetMapping("/dashboard")
    public ResponseEntity<EmpDashBoardDto> getDashboard(Authentication authentication) {
        User currentUser = (User) authentication.getPrincipal();
        Long empId = currentUser.getId();

        EmpDashBoardDto dashboard = employeeService.getEmployeeDashboard(empId);
        return ResponseEntity.ok(dashboard);
    }

        @PostMapping("/leaverequest")
        public ResponseEntity<String> leaverequest(@RequestBody LeaveRequestDto leaveRequestdto, Authentication authentication){

               User currentUser = (User) authentication.getPrincipal();

               employeeService.leaveRequest(leaveRequestdto,currentUser);
            return ResponseEntity.ok("leave Request is Intiated Succussfully");
        }

        @PatchMapping("/updateleaverequest/{id}")
       public ResponseEntity<String> updateleave(@RequestBody LeaveRequestDto leaveRequestDto,@PathVariable Long id){
            employeeService.updateLeaveRequest(leaveRequestDto,id);
            return ResponseEntity.ok("Leave request Updated Succussfully");
        }

        @DeleteMapping("/deletelr/{id}")
        public ResponseEntity<String> updateleave(@PathVariable Long id){
            employeeService.DeleteEmpLeaveRequest(id);
            return ResponseEntity.ok("Leave request Deleted Succussfully");
        }

}

