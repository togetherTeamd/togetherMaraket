package com.project.together.service;

import com.project.together.entity.Files;
import com.project.together.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FileService {
    private final FileRepository fileRepository;

    @Transactional
    public void save(Files file) {
        fileRepository.save(file);
    }

    public Files findById(Long id) {
        return fileRepository.findById(id);
    }

    public List<Files> findAll() {return fileRepository.findAll();}

    public List<Files> findByItem(Long itemId) {return fileRepository.findByItem(itemId);}
}
