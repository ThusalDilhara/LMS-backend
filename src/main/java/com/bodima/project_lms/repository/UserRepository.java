package com.bodima.project_lms.repository;

import com.bodima.project_lms.model.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<UserEntity,Integer> {

    Optional<UserEntity> findByEmail(String username);}
