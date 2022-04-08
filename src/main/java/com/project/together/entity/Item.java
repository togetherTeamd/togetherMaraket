package com.project.together.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Item {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    @Column(name = "item_name")
    private String name;
    @Column(name = "item_price")
    private int price;

    private LocalDate createdAt;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private ItemStatus itemStatus; //ENUM [SELLING(판매중), SOLD(판매 완료)]

}
