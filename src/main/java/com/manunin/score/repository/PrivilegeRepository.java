package com.manunin.score.repository;

import com.manunin.score.model.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
//@JaversSpringDataAuditable
public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {
}
