package com.project.together.service;

import com.project.together.entity.Category;
import com.project.together.entity.Item;
import com.project.together.entity.ItemCategory;
import com.project.together.repository.CategoryRepository;
import com.project.together.repository.ItemCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ItemCategoryRepository itemCategoryRepository;
    private final ItemService itemService;

    @Transactional
    public void save(Category category) {
        categoryRepository.save(category);
    }

    @Transactional
    public Long addCategory(Long itemId, Long categoryId ) {
        //엔티티 조회
        Item item = itemService.findOne(itemId);

        Category category = categoryRepository.findById(categoryId).get(0);

        ItemCategory itemCategory = ItemCategory.createItemCategory(category, item);

        itemCategoryRepository.save(itemCategory);

        return category.getId();
    }

    @Transactional
    public void createCategory() {
        Category category = new Category();
        category.setCategoryName("인기상품");
        categoryRepository.save(category);

        Category category2 = new Category();
        category2.setCategoryName("디지털기기");
        categoryRepository.save(category2);

        Category category3 = new Category();
        category3.setCategoryName("가구/인테리어");
        categoryRepository.save(category3);

        Category category4 = new Category();
        category4.setCategoryName("스포츠/레저");
        categoryRepository.save(category4);

        Category category5 = new Category();
        category5.setCategoryName("의류/잡화");
        categoryRepository.save(category5);

        Category category6 = new Category();
        category6.setCategoryName("게임/취미");
        categoryRepository.save(category6);

        Category category7 = new Category();
        category7.setCategoryName("도서/티켓/음반");
        categoryRepository.save(category7);

        Category category8 = new Category();
        category8.setCategoryName("모바일상품권");
        categoryRepository.save(category8);

        Category category9 = new Category();
        category9.setCategoryName("기타중고물품");
        categoryRepository.save(category9);
    }

    public Category findByName(Long categoryId) {
        return categoryRepository.findById(categoryId).get(0);
    }

    public List<Category> findAll() {
       return categoryRepository.findAll();
    }

    public List<ItemCategory> findAllItemCategory() {
        return itemCategoryRepository.findAll();
    }

}
