package com.example.kidsplanner.repository;

import com.example.kidsplanner.model.Period;
import com.example.kidsplanner.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PeriodRepository extends JpaRepository<Period, Long> {
    Optional<Period> findPeriodByUser(User user);

    Period findPeriodByStartDate(LocalDate date);

    List<Period> findAllPeriodsByUserId(Long userId);


}
