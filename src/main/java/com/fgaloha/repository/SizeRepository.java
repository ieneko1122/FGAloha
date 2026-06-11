package com.fgaloha.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fgaloha.entity.Size;

@Repository
public interface SizeRepository extends JpaRepository<Size, Long>{

}
