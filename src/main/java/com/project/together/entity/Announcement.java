package com.project.together.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class Announcement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="announcement_id")
    private Long id;

    @Column(name = "announcement_title")
    private String title;

    @Column(name = "announcement_text")
    private String text;

    private LocalDateTime createdAt;

}
