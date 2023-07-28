package com.example.SpringSecurityJWT.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.SpringSecurityJWT.models.UserEntity;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
	
	Optional<UserEntity> findByUserName(String userName);
	
	@Query("select u from UserEntity u where u.userName = ?1")
	Optional<UserEntity> getName(String userName);
}
