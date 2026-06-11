package com.fgaloha.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fgaloha.entity.CartItem;
import com.fgaloha.entity.User;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
	List<CartItem> findByUser(User user);

	void deleteByUser(User user);
}
