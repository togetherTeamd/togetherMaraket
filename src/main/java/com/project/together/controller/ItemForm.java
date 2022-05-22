package com.project.together.controller;

import com.project.together.entity.Address;
import com.project.together.entity.DealForm;
import com.project.together.entity.Enul;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;

@Data
public class ItemForm {

    private Long id;

    @NotEmpty(message = "상품 이름을 입력해 주세요")
    private String name;
    @NotEmpty(message = "상품 가격을 입력해 주세요")
    private int price;
    @NotEmpty(message = "상품 설명을 해주세요")
    private String contents;
    @NotEmpty(message = "협상 가능여부를 선택해주세요")
    private Enul enul;
    @NotEmpty(message = "거래 형태를 선택해주세요")
    private DealForm dealForm;
    @NotEmpty(message = "상품 등급을 설정해주세요")
    private String itemLevel;

    private String city;

    @NotEmpty(message = "상세 주소를 넣어주세요")
    private String street;

    @NotEmpty(message = "주소를 검색해주세요")
    private String zipcode;

    private String lat;
    private String lon;


}
