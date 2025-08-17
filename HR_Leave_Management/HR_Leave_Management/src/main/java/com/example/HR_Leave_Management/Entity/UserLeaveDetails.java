package com.example.HR_Leave_Management.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserLeaveDetails {

      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private Long id;

      private int MaxNoLeaves;
      private int LeavesTaken;
      private int balanceLeaves;
      private int LeavesPerYear;

      @OneToOne
      @JoinColumn(name = "user_id")
      private User user;

}