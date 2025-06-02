package com.springApp.Jpa.service;

import com.springApp.Jpa.entity.User;
import com.springApp.Jpa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public Iterable<User> saveAll(List<User> users){
        return repository.saveAll(users);
    }

    public Iterable<User> findAllUsers(){
        return repository.findAll();
    }

    public void deleteUser(Long id){
        repository.deleteById(id);
    }
}
