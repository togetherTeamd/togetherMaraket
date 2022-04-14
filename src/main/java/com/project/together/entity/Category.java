package com.project.together.entity;

import lombok.Data;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.*;
@Entity
@Data
public class Category {

    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    @OneToMany(mappedBy = "category")
    private List<ItemCategory> itemCategories = new ArrayList<>();

    /*@Column(name = "category_name", unique = true)
    private CategoryList categoryName;*/
    @Column(name = "category_name", unique = true)
    private String categoryName;


    /*@ManyToMany
    @JoinTable(name = "category_item",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id"))
    @Cascade(org.hiberna    te.annotations.CascadeType.ALL)
    private List<Item> items = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

    public void addChildCategory(Category child) {
        this.child.add(child);
        this.setParent(this);
    }*/

}
