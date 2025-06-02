package com.springApp.Jpa.repository;

import com.springApp.Jpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserJpaRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    List<User> findByActive(boolean active);
    List<User> findAllByOrderByUsernameAsc();
    List<User> findByActiveOrderByUsernameAsc(boolean active);
    List<User> findByLocalDateBetween(LocalDate start, LocalDate end);
    List<User> findByUsernameContaining(String text);
    List<User> findByUsernameStartingWith(String text);

    @Query("SELECT u FROM User u WHERE u.age > 30")
    List<User> findUsersOver30();

    @Query(value = "SELECT * FROM users WHERE array_get > 30", nativeQuery = true)
    List<User> findUsersOver30NativeQuery();
}
