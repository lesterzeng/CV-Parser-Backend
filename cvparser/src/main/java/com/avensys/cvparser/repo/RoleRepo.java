package com.avensys.cvparser.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.avensys.cvparser.entity.Role;

public interface RoleRepo extends JpaRepository<Role, Long> {
	Optional<Role> findByName(String name);

}
