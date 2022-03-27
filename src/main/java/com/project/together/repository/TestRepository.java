package com.project.together.repository;

import com.project.together.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;

// 테스트
public interface TestRepository extends JpaRepository <Test, String>{

}
