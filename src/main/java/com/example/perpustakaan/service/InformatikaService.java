package com.example.perpustakaan.service;

import com.example.perpustakaan.model.Informatika;
import com.example.perpustakaan.repository.InformatikaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InformatikaService {

    @Autowired
    private InformatikaRepository informatikaRepository;

    public List<Informatika> getAllInformatika() {
        return informatikaRepository.findAll();
    }

    public Optional<Informatika> getInformatikaById(Integer id) {
        return informatikaRepository.findById(id);
    }

    public Informatika saveInformatika(Informatika informatika) {
        return informatikaRepository.save(informatika);
    }

    public void deleteInformatika(Integer id) {
        informatikaRepository.deleteById(id);
    }
}
