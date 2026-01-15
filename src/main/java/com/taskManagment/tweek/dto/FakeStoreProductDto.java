package com.taskManagment.tweek.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
public class FakeStoreProductDto {
    private long id;
    private String title;
    private int price;
    private String description;
    private String image;
    private String category;
}