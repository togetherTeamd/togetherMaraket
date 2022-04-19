package com.project.together.entity;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class ReviewContents {
    @Id@Column(name = "review_contents_id")
    @GeneratedValue
    private Long id;

    private String contents;

}
