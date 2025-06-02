package com.springApp.Jpa.service;

import com.springApp.Jpa.entity.User;
import com.springApp.Jpa.repository.UserJpaRepository;
import com.springApp.Jpa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository repository;
    private final UserJpaRepository jpaRepository;

    @Autowired
    public UserService(UserRepository repository, UserJpaRepository jpaRepository) {
        this.repository = repository;
        this.jpaRepository = jpaRepository;
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

    public Optional<User> findByUsername(String username){
        return jpaRepository.findByUsername(username);
    }

    public User addUser(User user){
        return jpaRepository.save(user);
    }
}
