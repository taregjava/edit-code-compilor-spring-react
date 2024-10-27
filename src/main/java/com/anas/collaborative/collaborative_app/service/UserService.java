package com.anas.collaborative.collaborative_app.service;



import com.anas.collaborative.collaborative_app.entity.User;
import com.anas.collaborative.collaborative_app.entity.UserEntity;

import java.util.Optional;

public interface UserService {

    Optional<UserEntity> findByEmail(String email);

    void save(UserEntity user);


}
