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
            "FROM User u " +
            "WHERE :filter is null " +
            "   or u.username like %:filter% " +
            "   or u.email like %:filter%" +
            "   or u.firstName like %:filter%" +
            "   or u.lastName like %:filter%")
    Page<User> findAllByFilterWithPagination(@Param("filter") String filter, Pageable pageable);

    @Query("SELECT count(u) " +
            "FROM User u " +
            "WHERE :filter is null " +
            "   or u.username like %:filter% " +
            "   or u.email like %:filter%" +
            "   or u.firstName like %:filter%" +
            "   or u.lastName like %:filter%")
    long countByFilter(@Param("filter") String filter);

    @Query("SELECT u " +
            "FROM User u WHERE :filter is null " +
            "   or u.username like %:filter% " +
            "   or u.email like %:filter%")
    List<User> findAllByInputString(@Param("filter") String filter);
}
