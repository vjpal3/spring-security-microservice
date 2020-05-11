package com.vrishalipal.microservices.securityjwtservice.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.vrishalipal.microservices.securityjwtservice.model.AppUser;


@Repository
public interface AppUserRepository extends CrudRepository<AppUser, Long> {

	
}
