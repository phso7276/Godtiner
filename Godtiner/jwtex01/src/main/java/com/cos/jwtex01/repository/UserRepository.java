package com.cos.jwtex01.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.jwtex01.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	@Query("SELECT m FROM User m WHERE m.email = :email")
	User findByEmail(String email);

	boolean existsByEmail(String email);
}
