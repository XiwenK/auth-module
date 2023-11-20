package com.manunin.score.repository;

import com.manunin.score.model.User;
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
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);

    @Query("SELECT u " +
            "FROM User u WHERE :inputString is null or u.username like %:inputString% or u.email like %:inputString%")
    Page<User> findAllByInputStringWithPagination(@Param("inputString") String inputString, Pageable pageable);

    @Query("SELECT u " +
            "FROM User u WHERE :inputString is null or u.username like %:inputString% or u.email like %:inputString%")
    List<User> findAllByInputString(@Param("inputString") String inputString);
}
