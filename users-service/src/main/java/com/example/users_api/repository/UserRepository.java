package com.example.users_api.repository;

import com.example.users_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByEmailAndIdNot(String email, Long id);
    List<User> findByEmailContainingIgnoreCase(String email);

    @Query("""
        SELECT u
        FROM User u
        WHERE LOWER(u.email) LIKE LOWER(CONCAT( "%", :email, "%"))
            AND LOWER(u.name) LIKE LOWER(CONCAT( :name, "%"))
    """)
    List<User> searchByEmailAndName(@Param("email") String email, @Param("name")String name);

}
