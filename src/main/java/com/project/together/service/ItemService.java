package com.project.together.service;

import com.project.together.entity.Item;
import com.project.together.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }//상품 이름, 상품 카테고리

    public List<Item> findById(String name) {
        return itemRepository.findByName(name);
    }

    public Item findOne(Long itemIdx) {
        return itemRepository.findOne(itemIdx);
    }
}
