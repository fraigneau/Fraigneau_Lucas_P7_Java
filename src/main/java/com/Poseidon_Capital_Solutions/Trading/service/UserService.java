package com.Poseidon_Capital_Solutions.Trading.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.Poseidon_Capital_Solutions.Trading.Repository.UserRepository;
import com.Poseidon_Capital_Solutions.Trading.model.User;

public class UserService implements GenericService<User> {

    private UserRepository userRepository;

    public UserService() {
    }

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(int id) {
        return userRepository.findById(id).get();
    }

    @Override
    public User save(User Object) {
        return userRepository.save(Object);
    }

    @Override
    public void delete(User Object) {
        userRepository.delete(Object);
    }

}
