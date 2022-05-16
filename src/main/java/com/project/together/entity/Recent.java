package com.project.together.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Recent {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_idx")
    private Long userIdx;

    @Column(name = "item_id")
    private Long itemId;


}
