package com.poseidoncapitalsolutions.trading.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.poseidoncapitalsolutions.trading.dto.UserDTO;
import com.poseidoncapitalsolutions.trading.mapper.UserMapper;
import com.poseidoncapitalsolutions.trading.model.User;
import com.poseidoncapitalsolutions.trading.repository.UserRepository;

@Service
public class UserService implements GenericService<User> {

    private UserRepository userRepository;
    private UserMapper userMapper;
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
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
    public User save(User object) {
        object.setPassword(encodePassword(object.getPassword()));
        return userRepository.save(object);
    }

    @Override
    public void delete(User object) {
        userRepository.delete(object);
    }

    public List<UserDTO> findByid(List<User> users) {
        return users.stream()
                .map(userMapper::toDto)
                .toList();
    }

    public void update(UserDTO userDTO) {
        userRepository.save(merge(userDTO));
    }

    private User merge(UserDTO userDTO) {
        User user = findById(userDTO.getId());
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setFullname(userDTO.getFullname());
        user.setRole(userDTO.getRole());
        return user;
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
