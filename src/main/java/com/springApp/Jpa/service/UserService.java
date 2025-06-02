package com.springApp.Jpa.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springApp.Jpa.dto.UserDto;
import com.springApp.Jpa.entity.User;
import com.springApp.Jpa.exception.EmailSendingException;
import com.springApp.Jpa.exception.UserServiceException;
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
    private final ObjectMapper mapper;
    private final EmailService emailService;

    @Autowired
    public UserService(UserRepository repository, UserJpaRepository jpaRepository, ObjectMapper mapper, EmailService emailService) {
        this.repository = repository;
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
        this.emailService = emailService;
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

    public User createUserFromDto(UserDto userDto) {
        User user = mapper.convertValue(userDto, User.class);
        User savedUser = jpaRepository.save(user);
        String subject = "Creating user";
        String body = "Dear " + savedUser.getFirstName() + " Thank you for registration! \n your username is " + savedUser.getUsername();

        try {
            emailService.sendEmail(savedUser.getEmail(), subject, body);
        } catch (EmailSendingException e) {
            System.out.println(e.getMessage());
            throw new UserServiceException("User created succesfully but error on sending email", "ERROR_ON_EMAIL_SENDING");
        }

        return savedUser;
    }
}
