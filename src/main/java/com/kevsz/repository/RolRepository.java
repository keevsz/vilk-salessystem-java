package com.kevsz.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kevsz.model.Rol;


@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {
	Optional<Rol> findByDescription(String description);

	boolean existsByDescription(String description);
}
