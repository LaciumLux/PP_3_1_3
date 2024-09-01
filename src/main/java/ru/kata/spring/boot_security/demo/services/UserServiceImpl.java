package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserServiceImpl {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;


    @Autowired
    public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Transactional
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }


    @Transactional
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    public List<User> showAllUser() {
        return userRepository.findAll();
    }

    public User getUserById(long id) {
        return userRepository.getById(id);
    }

    public User getUserByUsername(String name) {
        return userRepository.findByUsername(name);
    }

}