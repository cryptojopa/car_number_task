package com.jobtest.jobtest.repository;

import com.jobtest.jobtest.model.CarNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CarNumberRepository extends JpaRepository<CarNumber, Long> {

    CarNumber findById(long id);

    @Query("SELECT c FROM CarNumber c WHERE c.alreadyUsed = :value ORDER BY RANDOM() LIMIT 1")
    CarNumber findRandomCarNumberByAlreadyUsed(boolean value);
}
