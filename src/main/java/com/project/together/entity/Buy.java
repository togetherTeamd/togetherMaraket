package com.project.together.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Buy {

    @Id
    @GeneratedValue @Column(name = "buy_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userIdx")
    private User user; //구매자

    @OneToMany(mappedBy = "buy", cascade = CascadeType.ALL)
    private List<BuyItem> buyItems = new ArrayList<>();

    private LocalDateTime buyDate;

    /*
    연관 관계 메서드
     */
    public void setUser(User user) {
        this.user = user;
        user.getBuyList().add(this);
    }

    public void addBuyItem(BuyItem buyItem) {
        buyItems.add(buyItem);
        buyItem.setBuy(this);
    }

    /*
    생성 메서드
     */
    public static Buy createBuy(User user, BuyItem... buyItems) {
        Buy buy = new Buy();
        buy.setUser(user);
        for (BuyItem buyItem : buyItems) {
            buyItem.setStatus();
            buy.addBuyItem(buyItem);
        }
        buy.setBuyDate(LocalDateTime.now());
        return buy;
    }

}
