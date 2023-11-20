package com.manunin.score.repository;

import com.manunin.score.model.Role;
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
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);

    Boolean existsByName(String username);

    @Query("SELECT r FROM Role r WHERE :inputString is null or r.name like %:inputString%")
    Page<Role> findAllByInputStringWithPagination(@Param("inputString") String inputString, Pageable pageable);

    @Query("SELECT r FROM Role r WHERE :inputString is null or r.name like %:inputString%")
    List<Role> findAllByInputString(@Param("inputString") String inputString);

}
