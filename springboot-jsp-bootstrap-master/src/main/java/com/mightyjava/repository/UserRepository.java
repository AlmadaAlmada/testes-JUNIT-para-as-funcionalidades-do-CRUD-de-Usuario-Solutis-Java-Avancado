package com.mightyjava.repository;

import com.mightyjava.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<Users, Long> {
	
	@Query("FROM Users WHERE userName=:username")
	Users findByUsername(@Param("username") String username);
}
