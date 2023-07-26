package com.example.SpringSecurityJWT.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.SpringSecurityJWT.models.RoleEntity;

@Repository
public interface RoleRepository extends CrudRepository<RoleEntity, Long>{

}
