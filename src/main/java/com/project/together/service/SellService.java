package com.project.together.service;

import com.project.together.entity.*;
import com.project.together.repository.BuyRepository;
import com.project.together.repository.ItemRepository;
import com.project.together.repository.SellRepository;
import com.project.together.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SellService {
    private final SellRepository sellRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    /**
     * 판매
     */
    @Transactional
    public Long sell(Long userIdx, Long itemId) {

        //엔티티 조회
        User user = userRepository.findByIdx(userIdx);
        Item item = itemRepository.findOne(itemId);

        //판매상품 생성
        SellItem sellItem = SellItem.createSellItem(item);

        //판매 생성
        Sell sell = Sell.createSell(user, sellItem);

        //판매 저장
        sellRepository.save(sell);

        return sell.getId();
    }

    public List<Sell> findAll() {
        return sellRepository.findAll();
    }
}
