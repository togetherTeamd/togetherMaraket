package com.project.together.service;

import com.project.together.entity.Test;
import com.project.together.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//테스트
@Service
public class TestService {

    @Autowired
    private TestRepository testRepository;

    public List<Test> testList(){
        return testRepository.findAll();
    }
}
