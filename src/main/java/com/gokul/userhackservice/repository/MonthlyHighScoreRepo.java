package com.gokul.userhackservice.repository;

import com.gokul.userhackservice.model.MontlyHighScore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MonthlyHighScoreRepo extends JpaRepository<MontlyHighScore,Long> {
    Optional<MontlyHighScore> findById(Long id);
}
