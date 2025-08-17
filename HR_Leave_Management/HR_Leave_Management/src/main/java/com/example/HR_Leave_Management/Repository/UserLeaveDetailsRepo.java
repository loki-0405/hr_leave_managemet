package com.example.HR_Leave_Management.Repository;

import com.example.HR_Leave_Management.Entity.UserLeaveDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLeaveDetailsRepo extends JpaRepository<UserLeaveDetails,Long> {
    UserLeaveDetails findByUserId(Long userId);

    @Modifying
    @Query("DELETE FROM UserLeaveDetails u WHERE u.user.id = :userId")
    void deleteByUserId(@Param("userId") Long userId);

}
