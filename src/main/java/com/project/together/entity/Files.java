package com.project.together.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
public class Files {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private Long id;

    private String filename;
    private String fileOriName;
    private String fileUrl;
    @Column(name = "item_id")
    private Long itemId;//어떤 아이템의 사진인지
}
