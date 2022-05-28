package com.project.together.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_idx")
    private Long id;

    @Column(name = "room_id")
    private String roomId;

    @Column(name = "writer")
    private String writer;

    @Column(name = "message")
    private String message;
}
