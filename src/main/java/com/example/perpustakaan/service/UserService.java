package com.example.perpustakaan.service;

import com.example.perpustakaan.model.Login;
import com.example.perpustakaan.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Simpan user dengan password yang di-hash
    public Login saveUser(String nama, String npm, String rawPassword) {
        String hashedPassword = passwordEncoder.encode(rawPassword); // Hash password
        Login user = new Login();
        user.setNama(nama);
        user.setNpm(npm);
        user.setPassword(hashedPassword); // Simpan password yang di-hash
        return userRepository.save(user);
    }

    public Login findByNpm(String npm) {
        return userRepository.findByNpm(npm);
    }

    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
