package com.anas.collaborative.collaborative_app.service.impl;


import com.anas.collaborative.collaborative_app.entity.User;
import com.anas.collaborative.collaborative_app.entity.UserEntity;
import com.anas.collaborative.collaborative_app.repository.UserEntityRepository;
import com.anas.collaborative.collaborative_app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserEntityRepository userEntityRepository;

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return userEntityRepository.findByEmail(email);
    }

    @Override
    public void save(UserEntity user) {
        userEntityRepository.save(user);
    }


}
