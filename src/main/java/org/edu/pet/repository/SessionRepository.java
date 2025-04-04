package org.edu.pet.repository;

import org.edu.pet.model.UserSession;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SessionRepository extends CrudRepository<UserSession, UUID> {

}