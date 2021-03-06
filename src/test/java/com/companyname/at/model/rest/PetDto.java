package com.companyname.at.model.rest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PetDto {
    private int id;
    private CategoryDto categoryDto;
    private String name;
    private List<String> photoUrls;
    private List<Tag> tags;
    private String status;
}