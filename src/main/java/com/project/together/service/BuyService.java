package com.project.together.service;

import com.project.together.entity.Buy;
import com.project.together.entity.BuyItem;
import com.project.together.entity.Item;
import com.project.together.entity.User;
import com.project.together.repository.BuyRepository;
import com.project.together.repository.ItemRepository;
import com.project.together.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BuyService {

    private final BuyRepository buyRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    /**
     * 구매
     */
    @Transactional
    public Long buy(Long userIdx, Long itemId) {

        //엔티티 조회
        User user = userRepository.findByIdx(userIdx);
        Item item = itemRepository.findOne(itemId);

        //구매상품 생성
        BuyItem buyItem = BuyItem.createBuyItem(item);

        //구매 생성
        Buy buy = Buy.createBuy(user, buyItem);

        //구매 저장
        buyRepository.save(buy);

        return buy.getId();
    }

    /**
     * 검색
     */

    /*public List<Buy> findBuys(BuySearch buySearch) {
        return BuyRepository.findAll(buySearch);
    }*/
}
