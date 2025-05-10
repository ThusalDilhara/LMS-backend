package com.bodima.project_lms.repository;

import com.bodima.project_lms.model.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface AuthRepository extends MongoRepository<UserEntity, String> {

    UserEntity findFirstByEmailAndActiveTrueAndVerifiedTrue(String email);

    UserEntity findByEmail(String email);

    // Updated method name to match MongoDB naming conventions
    List<UserEntity> findByFirstName(String name);

    Page<UserEntity> findAllByRole(String role, Pageable pageable);

    // Updated method name and ID type to String
    UserEntity findByIdAndActiveTrue(String id);

    // MongoDB equivalent for counting users with null role
    @Query(value = "{ 'role': { $exists: false } }", count = true)
    long countUsersWithNullRole();

    // MongoDB aggregation for counting users by role
    // This returns MongoDB Document objects instead of Object[]
    @Query(value = "{ $group: { _id: '$role', count: { $sum: 1 } } }")
    List<org.bson.Document> countUsersByRole();
}