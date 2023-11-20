package com.manunin.score.repository;

import com.manunin.score.model.Space;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
//@JaversSpringDataAuditable
public interface SpaceRepository extends JpaRepository<Space, Long> {
    Optional<Space> findByName(String name);
    Boolean existsByName(String username);

    @Query("SELECT s FROM Space s WHERE :inputString is null or s.name like %:inputString% or s.description like %:inputString%")
    Page<Space> findAllByInputStringWithPagination(@Param("inputString") String inputString, Pageable pageable);

    @Query("SELECT s FROM Space s WHERE :inputString is null or s.name like %:inputString% or s.description like %:inputString%")
    List<Space> findAllByInputString(@Param("inputString") String inputString);
}
