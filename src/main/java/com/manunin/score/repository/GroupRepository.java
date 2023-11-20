package com.manunin.score.repository;

import com.manunin.score.model.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    Optional<Group> findById(Long id);

    Boolean existsByName(String username);

    @Query("SELECT u " +
            "FROM Group u WHERE :inputString is null or u.name like %:inputString%")
    Page<Group> findAllByInputStringWithPagination(@Param("inputString") String inputString, Pageable pageable);

    @Query("SELECT u " +
            "FROM Group u WHERE :inputString is null or u.name like %:inputString%")
    List<Group> findAllByInputString(@Param("inputString") String inputString);

    Optional<Group> findByUrl(String url);
}
