package com.enam.repository;

import com.enam.model.User;
import com.enam.model.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByUsername(String username);
    
    Optional<User> findByEmail(String email);
    
    Boolean existsByUsername(String username);
    
    Boolean existsByEmail(String email);
    
    List<User> findByUserType(UserType userType);
    
    List<User> findByState(String state);
    
    List<User> findByDistrict(String district);
    
    @Query("SELECT u FROM User u WHERE u.isActive = true")
    List<User> findActiveUsers();
    
    @Query("SELECT u FROM User u WHERE u.userType = ?1 AND u.state = ?2")
    List<User> findByUserTypeAndState(UserType userType, String state);
    
    @Query("SELECT COUNT(u) FROM User u WHERE u.userType = ?1")
    Long countByUserType(UserType userType);
}