package com.example.perpustakaan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.perpustakaan.model.Login;

@Repository
public interface UserRepository extends JpaRepository<Login, Long> {
    Login findByNpm(String npm);
}
    