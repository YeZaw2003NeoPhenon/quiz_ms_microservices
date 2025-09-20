package com.example.user_service.service;

import com.example.user_service.dao.UserDao;
import com.example.user_service.dto.UserRequest;
import com.example.user_service.dto.UserResponse;
import com.example.user_service.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDao userDao;

    public UserResponse createUser(UserRequest userRequest){
        User user = new User();
        user.setName(userRequest.getName());
        user.setGender(userRequest.getGender());
        userDao.save(user);
        return new UserResponse(user.getName(), user.getGender());
    }

    public UserResponse getUserById(Integer id){
       User user =  userDao.findById(id).orElseThrow(()-> new RuntimeException("User with id "+id+" not found"));
       return new UserResponse(user.getName(), user.getGender());
    }

    public UserResponse addQuizToUser(Integer userId, Integer quizId){
        User user = userDao.findById(userId)
                           .orElseThrow(() -> new RuntimeException("User not found"));
        user.getQuizIds().add(quizId);
        userDao.save(user);
        return new UserResponse(user.getName(), user.getGender());
    }

}
