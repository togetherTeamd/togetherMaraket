package com.project.together.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class ItemCategory {
    @Id
    @GeneratedValue
    @Column(name = "item_category_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category; //이 아이템카테고리의 주인

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private Long categoryIds;
    private Long itemIds;
    private String categoryName;

    //연관관계 설정 메소드
    public void setCategory(Category category) {
        this.category = category;
        category.getItemCategories().add(this);
    }

    public void setItem(Item item) {
        this.item = item;
        item.getItemCategories().add(this);
    }

    //생성 메소드
    public static ItemCategory createItemCategory(Category category, Item item) {
        ItemCategory itemCategory = new ItemCategory();
        itemCategory.setCategory(category);
        itemCategory.setItem(item);
        itemCategory.setCategoryIds(category.getId());
        itemCategory.setItemIds(item.getId());
        itemCategory.setCategoryName(category.getCategoryName());

        return itemCategory;
    }


}
