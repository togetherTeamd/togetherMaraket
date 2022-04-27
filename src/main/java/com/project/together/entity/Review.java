package com.project.together.entity;

import lombok.Data;

import javax.persistence.*;


@Entity
@Data
public class Review {

    @Id@GeneratedValue
    @Column(name = "review_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userIdx")
    private User user;

    @OneToOne(mappedBy = "review", fetch = FetchType.LAZY)
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_contents_id")
    private ReviewContents reviewContents;

    @Column(name = "review_message")
    private String message;

    public void setUser(User user) {
        this.user = user;
        user.getReviewList().add(this);
    }

    public void setItem(Item item) {
        this.item = item;
        item.setReview(this);
    }

    public static Review createReview(User user, Item item, ReviewContents reviewContents, String message) {
        Review review = new Review();
        review.setUser(user);
        review.setItem(item);
        review.setReviewContents(reviewContents);
        review.setMessage(message);

        return review;
    }

}
