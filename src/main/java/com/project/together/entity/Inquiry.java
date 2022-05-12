package com.project.together.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
//@NoArgsConstructor
public class Inquiry {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inquiry_id")
    private Long id;

    @Column(name = "user_Idx")
    private Long userIdx;

    @Column(name = "inquiry_text")
    private String inquiryText;

    @Column(name = "inquiry_answer")
    private String inquiryAnswer;

    private LocalDateTime createdAt;

    private LocalDateTime answerAt;

}
