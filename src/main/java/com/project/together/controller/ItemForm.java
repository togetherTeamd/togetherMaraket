package com.project.together.controller;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@ToString
public class ItemForm {

    private Long id;

    @NotEmpty(message = "상품 이름을 입력해 주세요")
    private String name;
    @NotEmpty(message = "상품 가격을 입력해 주세요")
    private int price;
}
