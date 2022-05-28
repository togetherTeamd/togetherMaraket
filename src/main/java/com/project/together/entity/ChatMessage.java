package com.project.together.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Getter
@Setter
public class ChatMessage {

    private String roomId;
    private String writer;
    private String message;
}
