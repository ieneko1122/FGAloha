package com.fgaloha.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fgaloha.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
