package com.project.together.controller;

import com.project.together.config.auth.PrincipalDetails;
import com.project.together.entity.Item;
import com.project.together.entity.ItemCategory;
import com.project.together.entity.Review;
import com.project.together.entity.User;
import com.project.together.repository.ReviewContentsRepository;
import com.project.together.service.CategoryService;
import com.project.together.service.ItemService;
import com.project.together.service.ReviewService;
import com.project.together.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final UserService userService;
    private final ItemService itemService;
    private final ReviewContentsRepository reviewContentsRepository;

    @GetMapping("review/{itemId}/review")
    public String createReviewForm(Model model, @PathVariable Long itemId) {
        if(reviewContentsRepository.findAll().isEmpty()) {
            reviewService.createReview();
        }
        //해당 아이템 조회, 아이템 판매자 조회회
        Item item = itemService.findOne(itemId);
        User user = userService.findById(item.getSeller());

        model.addAttribute("user", user);//
        model.addAttribute("reviewContents", reviewContentsRepository.findAll());//리뷰 목록
        model.addAttribute("form", new ReviewForm());

        return "review/reviewPopup";
    }

    @PostMapping(value ="/review/{itemId}/review")
    public String createReview(@RequestParam("reviewContentId") Long reviewContentsId,
                               @PathVariable Long itemId, @ModelAttribute("form") ReviewForm form, Model model) {
        //해당 아이템 조회, 아이템 판매자 조회회
        Item item = itemService.findOne(itemId);
        User user = userService.findById(item.getSeller());

        reviewService.addReview(itemId, user.getUserIdx(), reviewContentsId, form.getMessage());

        model.addAttribute("message", "정상적으로 처리되었습니다.");


        return "redirect:/";
    }

    @GetMapping(value = "/review")
    public String reviewList(Model model, @AuthenticationPrincipal PrincipalDetails user) {

        User loginUser = userService.findById(user.getUsername());

        List<Review> reviewList = reviewService.findByUser(loginUser.getUserIdx());


        model.addAttribute("user", userService.findByIdx(loginUser.getUserIdx()));
        model.addAttribute("reviewList", reviewList);

        return "review/reviewList";
    }
}
