package com.ums.repository;

import com.ums.bean.User;
import com.ums.bean.UserHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserHistoryRepository extends JpaRepository<UserHistory, Integer> {
    List<UserHistory> findByUserIdOrderByDateAsc(Integer userId);
}
