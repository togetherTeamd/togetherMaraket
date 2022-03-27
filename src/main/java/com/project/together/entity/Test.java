package com.project.together.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

//테스트
@Entity
@Data
public class Test {

    @Id
    private String id;

    private String pw;
}
