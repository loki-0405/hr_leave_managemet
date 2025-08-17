package com.example.HR_Leave_Management.Controller;

import com.example.HR_Leave_Management.DTO.HrDashboardDto;
import com.example.HR_Leave_Management.DTO.UserLeaveDetailsDto;
import com.example.HR_Leave_Management.Entity.*;
import com.example.HR_Leave_Management.Repository.UserRepository;
import com.example.HR_Leave_Management.Service.HrService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hr")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8081")
public class HrController {

    private final HrService hrService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/dashboard")
    public ResponseEntity<String> hrDashboard() {
        return ResponseEntity.ok("Welcome HR");
    }

    @PostMapping("/addemp")
    public ResponseEntity<String> addEmployee(@RequestBody User user, Authentication authentication) {
        try {

            if (userRepository.findByEmail(user.getEmail()).isPresent()) {
                return ResponseEntity.badRequest().body("Email already exists!");
            }

            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);

            User currentUser = (User) authentication.getPrincipal();
            user.setManagerId(currentUser.getId());


            if (user.getRoleCategory() == null) {
                user.setRoleCategory(RoleCategory.EMPLOYEE); // Default role
            }

            if (user.getEmpType() == null) {
                user.setEmpType(EmpType.FULLTIME);
            }

            if (user.getDepartment() == null) {
                user.setDepartment(Department.TECHNICAL);
            }

            if (user.getGender() == null) {
                user.setGender(Gender.MALE);
            }

            if (user.getStatus() == null) {
                user.setStatus(Status.Active);
            }


            User savedUser = userRepository.save(user);

            return ResponseEntity.ok("Employee added successfully with id"+savedUser.getId());



        } catch (IllegalArgumentException e) {
            if (e.getMessage().contains("No enum constant")) {
                return ResponseEntity.badRequest().body("Invalid enum value. Error: " + e.getMessage());
            }
            return ResponseEntity.badRequest().body("Invalid argument: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error adding employee: " + e.getMessage());
        }
    }

    @PutMapping("/updateemp/{id}")
    public ResponseEntity<String> updateEmployee(@PathVariable Long id, @RequestBody User user) {
        try {
            String result = hrService.updateEmployee(id, user);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            System.out.println("Error updating employee: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error updating employee: " + e.getMessage());
        }
    }

    @PatchMapping("/updateemp/{id}")
    public ResponseEntity<String> partialUpdateEmployee(@PathVariable Long id, @RequestBody User user) {
        try {
            String result = hrService.partialUpdateEmployee(id, user);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            System.out.println("Error updating employee: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error updating employee: " + e.getMessage());
        }
    }

    @DeleteMapping("/deleteemp/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        try {
            String result = hrService.deleteEmployee(id);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            System.out.println("Error deleting employee: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error deleting employee: " + e.getMessage());
        }
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<?> getEmployee(@PathVariable Long id) {
        try {
            User user = hrService.getEmployeeById(id);
            return ResponseEntity.ok(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            System.out.println("Error fetching employee: " + e.getMessage());
            return ResponseEntity.badRequest().body("Error fetching employee: " + e.getMessage());
        }
    }

    @GetMapping("/employees")
    public ResponseEntity<?> getAllEmployees() {
        try {
            return ResponseEntity.ok(hrService.getAllEmployees());
        } catch (Exception e) {
            System.out.println("Error fetching employees: " + e.getMessage());
            return ResponseEntity.badRequest().body("Error fetching employees: " + e.getMessage());
        }
    }

    @PostMapping("/addleavedetails/{id}")
    public ResponseEntity<String> AddLeaveDetails(@RequestBody UserLeaveDetailsDto userLeaveDetailsDto,@PathVariable Long id) {
        hrService.AddEmpLeaveDetails(userLeaveDetailsDto,id);
        return ResponseEntity.ok("Successfully Added User Leave Details Of  Employee");
    }

    @PatchMapping("/updleavedetails/{id}")
    public ResponseEntity<String> UpdLeaveDetails(@RequestBody UserLeaveDetailsDto userLeaveDetailsDto,@PathVariable Long id) {
        hrService.UpdEmpLeaveDetails(userLeaveDetailsDto,id);
        return ResponseEntity.ok("Successfully Updated User Leave Details");
    }

    @DeleteMapping("/delleavedetails/{id}")
    public ResponseEntity<String> DelLeaveDetails(@PathVariable Long id) {
        hrService.DelEmpLeaveDetails(id);
        return ResponseEntity.ok("Successfully Deleted User Leave Details");
    }

    @PatchMapping("/approve/{id}")
    public ResponseEntity<String> approveLeave(@PathVariable Long id) {
         hrService.approveLeave(id);
        return ResponseEntity.ok("Leave Approved Successfully");
    }

    @PatchMapping("/reject/{id}")
    public ResponseEntity<String> rejectLeave(@PathVariable Long id) {
        hrService.rejectLeave(id);
        return ResponseEntity.ok("Leave Rejected Successfully");
    }

    @GetMapping("/hrdashboard")
    public ResponseEntity<?> getDashboard(Authentication authentication) {
        User currentHr = (User) authentication.getPrincipal();
        Long hrId = currentHr.getId();

        HrDashboardDto dashboard = hrService.getHrDashboard(hrId);
        return ResponseEntity.ok(dashboard);
    }

}