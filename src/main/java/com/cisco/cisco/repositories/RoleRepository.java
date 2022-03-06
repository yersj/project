package com.cisco.cisco.repositories;

import com.cisco.cisco.entities.AuthRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface RoleRepository extends JpaRepository<AuthRole,Long> {
    AuthRole findByRole(String role);
}
