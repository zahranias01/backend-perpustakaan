package com.example.perpustakaan.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "login")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Login {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String nama;

    @Column(unique = true, nullable = false)
    private String npm;
    
    @Column(nullable = false)
    private String password;
}
