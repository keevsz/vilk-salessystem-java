package com.kevsz.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kevsz.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
	Optional<Category> findByDescription(String description);

	boolean existsByDescription(String description);
}
