package com.expensetracker.repository;

import com.expensetracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Repository interface for User entity.
 * Provides database access methods for user operations.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * Find a user by their OpenID (unique identifier from OAuth).
     * Returns empty Optional if user doesn't exist.
     */
    Optional<User> findByOpenId(String openId);

    /**
     * Find a user by email address.
     * Returns empty Optional if user doesn't exist.
     */
    Optional<User> findByEmail(String email);
}
