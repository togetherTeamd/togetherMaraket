package com.project.together.service;

import com.project.together.entity.Item;
import com.project.together.entity.ItemStatus;
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
    private final BuyService buyService;

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

    public List<Item> findBySeller(String sellerId) {
        return itemRepository.findBySeller(sellerId);
    }

    public List<Item> findSellingItem() {
        return itemRepository.findSellingItem();
    }

    @Transactional
    public void updateItem(Long itemId, String name, int price) {
        Item item = itemRepository.findOne(itemId);
        item.setName(name);
        item.setPrice(price);
    }

    @Transactional
    public void setBuyer(Long userIdx, Long itemId) {//판매완료
        Item item = itemRepository.findOne(itemId);
        item.setItemStatus(ItemStatus.SOLD);
        buyService.buy(userIdx, itemId);
    }

}
