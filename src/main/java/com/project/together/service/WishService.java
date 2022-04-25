package com.project.together.service;

import com.project.together.entity.*;
import com.project.together.repository.ItemRepository;
import com.project.together.repository.UserRepository;
import com.project.together.repository.WishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WishService {

    private final WishRepository wishRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public Wish Wish(Long userIdx, Long itemId) {
        //엔티티 조회
        User user = userRepository.findByIdx(userIdx);
        Item item = itemRepository.findOne(itemId);

        //위시리스트에 추가할 아이템 생성
        WishItem wishItem = WishItem.createWishItem(item);

        //찜 생성
        Wish wish = Wish.createWish(user, wishItem);

        wishRepository.save(wish);

        return wish;
    }

    public int checkDuplicate(Long userIdx, Long itemId) {

        List<Wish> wishList = wishRepository.findDuplicate(userIdx, itemId);
        if(wishList.size() > 0) {
            return 1;
        }
        return 0;
    }

    public List<Wish> findByUser(Long userIdx) {
        return wishRepository.findByUser(userIdx);
    }

    @Transactional
    public void removeWish(Long userIdx, Long wishId) {
        wishRepository.findByUserItem(userIdx, wishId).get(0).getWishItem().getItem().removeWishCount();
        wishRepository.wishCancel(userIdx, wishId);
    }


}