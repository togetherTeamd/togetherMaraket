package com.project.together.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_idx")
    private Long id;

    @Column(name = "room_id")
    private String roomId;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "seller_id")
    private String sellerId;

    @Column(name = "item_idx")
    private Long itemIdx;
}
