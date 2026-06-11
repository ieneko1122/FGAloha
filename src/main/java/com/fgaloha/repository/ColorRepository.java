package com.fgaloha.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fgaloha.entity.Color;

@Repository
public interface ColorRepository extends JpaRepository<Color, Long>{

}
