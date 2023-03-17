package com.example.Editique.entity;

import lombok.Builder;

import java.util.List;

@Builder
public class Flux {
    private String name ;
    private List<Object> value;

}
