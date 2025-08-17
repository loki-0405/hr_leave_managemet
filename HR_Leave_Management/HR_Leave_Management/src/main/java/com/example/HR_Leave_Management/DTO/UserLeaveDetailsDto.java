package com.example.HR_Leave_Management.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLeaveDetailsDto {

    private int maxNoLeaves;
    private int leavesTaken;
    private int balanceLeaves;
    private int leavesPerYear;

}
