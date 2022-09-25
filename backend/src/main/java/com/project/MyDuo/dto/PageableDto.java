package com.project.MyDuo.dto;

import lombok.Data;

@Data
public class PageableDto {
    private Long registrationTime;
    private Integer size, page;
    private String sort;
}
