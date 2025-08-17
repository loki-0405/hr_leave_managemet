package com.example.HR_Leave_Management.Controller;

import com.example.HR_Leave_Management.DTO.LoginRequest;
import com.example.HR_Leave_Management.DTO.LoginResponse;
import com.example.HR_Leave_Management.Entity.*;
import com.example.HR_Leave_Management.Repository.UserRepository;
import com.example.HR_Leave_Management.Util.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8081")
public class AuthController {

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    // ✅ LOGIN ENDPOINT WITH BETTER ERROR HANDLING
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        try {
            System.out.println("Attempting login for email: " + loginRequest.getEmail());

            // Check if user exists first
            User userFromDb = userRepository.findByEmail(loginRequest.getEmail())
                    .orElse(null);

            if (userFromDb == null) {
                System.out.println("User not found in database");
                return ResponseEntity.badRequest().body(
                        new LoginResponse(null, null, RoleCategory.INVALID)
                );
            }

            System.out.println("User found: " + userFromDb.getEmpName()); // Note: field is EmpName
            System.out.println("User role: " + userFromDb.getRoleCategory());
            System.out.println("Password check - Raw password: " + loginRequest.getPassword());
            System.out.println("Password check - Encoded in DB: " + userFromDb.getPassword());

            // Check password manually for debugging
            boolean passwordMatches = passwordEncoder.matches(loginRequest.getPassword(), userFromDb.getPassword());
            System.out.println("Password matches: " + passwordMatches);

            // Authenticate email & password
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );

            System.out.println("Authentication successful");

            // Extract user details
            User user = (User) authentication.getPrincipal();

            // Generate JWT token
            String token = jwtService.generateToken(user);

            System.out.println("Token generated successfully");

            // Return token, name, role
            RoleCategory role = user.getRoleCategory(); // Direct assignment, no valueOf needed
            String name = user.getEmpName(); // Note: field is EmpName in your entity
            System.out.println("Final role: " + role);
            System.out.println("Final name: " + name);

            LoginResponse response = new LoginResponse(token, name, role);
            return ResponseEntity.ok(response);

        } catch (BadCredentialsException e) {
            System.out.println("Bad credentials: " + e.getMessage());
            return ResponseEntity.badRequest().body(
                    new LoginResponse(null, null, RoleCategory.INVALID)
            );
        } catch (AuthenticationException e) {
            System.out.println("Authentication failed: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body(
                    new LoginResponse(null, null, RoleCategory.INVALID)
            );
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body(
                    new LoginResponse(null, null, RoleCategory.INVALID)
            );
        }
    }

    @PostMapping("/addemp")
    public ResponseEntity<String> addEmployee(@RequestBody User user,Authentication authentication) {
        try {
            // Optional: Check if email already exists
            if (userRepository.findByEmail(user.getEmail()).isPresent()) {
                return ResponseEntity.badRequest().body("Email already exists!");
            }

            System.out.println("Adding employee: " + user.getEmpName()); // Note: field is EmpName
            System.out.println("Email: " + user.getEmail());
            System.out.println("Raw password: " + user.getPassword());

            // Encrypt password
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);

            User currentUser = (User) authentication.getPrincipal();
            user.setManagerId(currentUser.getId());

            System.out.println("Encoded password: " + encodedPassword);



            // Set default values for required enum fields
            if (user.getRoleCategory() == null) {
                user.setRoleCategory(RoleCategory.EMPLOYEE); // Default role
            }

            // Set default values for other enum fields to avoid null constraint issues
            // You can adjust these defaults based on your business requirements
            if (user.getEmpType() == null) {
                 user.setEmpType(EmpType.FULLTIME); // Uncomment and set appropriate default
            }

            if (user.getDepartment() == null) {
                 user.setDepartment(Department.TECHNICAL); // Uncomment and set appropriate default
            }

            if (user.getGender() == null) {
                user.setGender(Gender.MALE); // Uncomment and set appropriate default
            }

            if (user.getStatus() == null) {
                 user.setStatus(Status.Active); // Uncomment and set appropriate default
            }

            System.out.println("Role category: " + user.getRoleCategory());
            System.out.println("Department: " + user.getDepartment());
            System.out.println("EmpType: " + user.getEmpType());
            System.out.println("Gender: " + user.getGender());
            System.out.println("Status: " + user.getStatus());

            User savedUser = userRepository.save(user);
            System.out.println("User saved with ID: " + savedUser.getId());

            return ResponseEntity.ok("Employee added successfully");

        } catch (Exception e) {
            System.out.println("Error adding employee: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error adding employee: " + e.getMessage());
        }
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Auth endpoint is working!");
    }

    // Debug endpoint to check user data
    @GetMapping("/debug/{email}")
    public ResponseEntity<String> debugUser(@PathVariable String email) {
        try {
            User user = userRepository.findByEmail(email).orElse(null);
            if (user == null) {
                return ResponseEntity.ok("User not found: " + email);
            }

            return ResponseEntity.ok(
                    "User found: " + user.getEmpName() + // Note: field is EmpName
                            ", Role: " + user.getRoleCategory() +
                            ", Password starts with: " + user.getPassword().substring(0, 10) + "..."
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}