package com.manunin.score.repository;

import com.manunin.score.model.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    Optional<Schedule> findByName(String username);
    Boolean existsByName(String username);

    @Query("SELECT u " +
            "FROM Group u WHERE :inputString is null or u.name like %:inputString%")
    Page<Schedule> findAllByInputStringWithPagination(@Param("inputString") String inputString, Pageable pageable);

    @Query("SELECT u " +
            "FROM Schedule u WHERE :inputString is null or u.name like %:inputString%")
    List<Schedule> findAllByInputString(@Param("inputString") String inputString);

}
