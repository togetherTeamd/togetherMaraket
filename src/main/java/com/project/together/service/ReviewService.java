package com.project.together.service;

import com.project.together.entity.Review;
import com.project.together.entity.ReviewContents;
import com.project.together.entity.User;
import com.project.together.repository.ReviewContentsRepository;
import com.project.together.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import com.project.together.entity.Item;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ItemService itemService;
    private final UserService userService;
    private final ReviewContentsRepository reviewContentsRepository;

    @Transactional
    public Long addReview(Long itemId, Long userIdx, Long reviewContentsId, String message) {
        //엔티티 조회
        Item item = itemService.findOne(itemId);
        User user = userService.findByIdx(userIdx);


        if(reviewContentsRepository.findAll().isEmpty()) {
            createReview();
        }

        ReviewContents reviewContents = reviewContentsRepository.findOne(reviewContentsId);

        if(reviewContents.getContents().equals("친절해요!")) {
            user.setKind(user.getKind() + 1);
        } else if (reviewContents.getContents().equals("상품이 사진 그대로에요!")) {
            user.setGoodPicture(user.getGoodPicture() + 1);
        } else {
            user.setGoodTime(user.getGoodTime() + 1);
        }
        user.setMannerScore(user.getMannerScore() + 1);

        //리뷰 생성
        Review review = Review.createReview(user, item, reviewContents, message);
        reviewRepository.save(review);

        return review.getId();
    }

    @Transactional
    public void createReview() {
        ReviewContents reviewContents = new ReviewContents();
        reviewContents.setContents("친절해요!");
        reviewContentsRepository.save(reviewContents);

        ReviewContents reviewContents2 = new ReviewContents();
        reviewContents2.setContents("상품이 사진 그대로에요!");
        reviewContentsRepository.save(reviewContents2);

        ReviewContents reviewContents3 = new ReviewContents();
        reviewContents3.setContents("시간 약속을 잘 지켜요!");
        reviewContentsRepository.save(reviewContents3);
    }

    public List<Review> findAll() {
        return reviewRepository.findAll();
    }

    public List<Review> findByUser(Long userIdx) {
        return reviewRepository.findByUser(userIdx);
    }

    public Review findOne(Long id) {
        return reviewRepository.findOne(id);
    }
}
