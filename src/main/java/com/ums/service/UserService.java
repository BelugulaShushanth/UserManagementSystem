package com.ums.service;

import com.ums.bean.User;
import com.ums.bean.UserHistory;
import com.ums.repository.UserHistoryRepository;
import com.ums.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserHistoryRepository userHistoryRepository;

    public List<UserHistory> processUserSignIn(User user){
        System.out.println(user.getId());
        if(user.getNoOfTimesLoggedIn() == null){
            user.setNoOfTimesLoggedIn(0);
        }
        user.setNoOfTimesLoggedIn(user.getNoOfTimesLoggedIn()+1);
        System.out.println(userRepository.save(user));
        List<UserHistory> userHistories = userHistoryRepository.findByUserIdOrderByDateAsc(user.getId());
        if(userHistories == null){
            userHistories = new ArrayList<>();
        }
        UserHistory userHistory = new UserHistory();
        userHistory.setDate(new Date());
        userHistory.setUserId(user.getId());
        userHistories.add(userHistory);
        userHistoryRepository.save(userHistories);
        return userHistoryRepository.findAll().stream()
                .filter(u -> u.getUserId().equals(user.getId()))
                .collect(Collectors.toList());
    }
}
