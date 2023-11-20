package com.manunin.score.repository;

import com.manunin.score.model.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {
    Optional<Resource> findById(Long id);

    Boolean existsByName(String username);

    @Query("SELECT u " +
            "FROM Resource u WHERE :inputString is null or u.name like %:inputString%")
    Page<Resource> findAllByInputStringWithPagination(@Param("inputString") String inputString, Pageable pageable);

    @Query("SELECT u " +
            "FROM Resource u WHERE :inputString is null or u.name like %:inputString%")
    List<Resource> findAllByInputString(@Param("inputString") String inputString);
}
