package com.poseidoncapitalsolutions.trading.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.poseidoncapitalsolutions.trading.dto.UserDTO;
import com.poseidoncapitalsolutions.trading.exception.ResourceNotFoundException;
import com.poseidoncapitalsolutions.trading.mapper.UserMapper;
import com.poseidoncapitalsolutions.trading.model.User;
import com.poseidoncapitalsolutions.trading.repository.UserRepository;

/**
 * Service class responsible for handling operations related to Users.
 * Provides methods for CRUD operations, mapping, and managing user data.
 */
@Service
public class UserService implements GenericService<User> {

    private UserRepository userRepository;
    private UserMapper userMapper;
    private PasswordEncoder passwordEncoder;

    /**
     * Constructs a UserService with the given repository, mapper, and password
     * encoder.
     * 
     * @param userRepository  The repository to interact with User data.
     * @param userMapper      The mapper to convert User entities to DTOs.
     * @param passwordEncoder The encoder to encrypt user passwords.
     */
    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Retrieves all Users from the repository.
     * 
     * @return A list of all Users.
     */
    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * Retrieves a User by its ID.
     * 
     * @param id The ID of the User to retrieve.
     * @return The User with the specified ID.
     * @throws ResourceNotFoundException If no User with the given ID is found.
     */
    @Override
    public User findById(int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));
    }

    /**
     * Saves a given User entity after encoding the password.
     * 
     * @param object The User entity to save.
     * @return The saved User entity.
     */
    @Override
    public User save(User object) {
        object.setPassword(encodePassword(object.getPassword()));
        return userRepository.save(object);
    }

    /**
     * Deletes the specified User entity.
     * 
     * @param object The User entity to delete.
     */
    @Override
    public void delete(User object) {
        userRepository.delete(object);
    }

    /**
     * Converts a list of User entities to a list of UserDTOs.
     * 
     * @param users The list of User entities to convert.
     * @return A list of UserDTOs corresponding to the provided Users.
     */
    public List<UserDTO> findByid(List<User> users) {
        return users.stream()
                .map(userMapper::toDto)
                .toList();
    }

    /**
     * Updates the User entity based on the given UserDTO.
     * 
     * @param userDTO The UserDTO containing updated information.
     */
    public void update(UserDTO userDTO) {
        userRepository.save(merge(userDTO));
    }

    /**
     * Merges the data from a UserDTO into an existing User entity.
     * 
     * @param userDTO The UserDTO containing the updated data.
     * @return The updated User entity.
     */
    private User merge(UserDTO userDTO) {
        User user = findById(userDTO.getId());
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setFullname(userDTO.getFullname());
        user.setRole(userDTO.getRole());
        return user;
    }

    /**
     * Encodes a plain password into a hashed format.
     * 
     * @param password The plain password to encode.
     * @return The encoded password.
     */
    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
