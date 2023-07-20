package com.TaskRabbit.Repository;

import com.TaskRabbit.Entity.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    PasswordResetToken findByToken(String token);
    void deleteByExpiryDateLessThan(Date now);

    void deleteByUserId(Long id);
}

