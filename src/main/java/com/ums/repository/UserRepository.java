package com.ums.repository;

import com.ums.bean.User;
import com.ums.bean.UserHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsernameAndPassword(String username, String password);
    Optional<User> findByUsername(String username);
}
