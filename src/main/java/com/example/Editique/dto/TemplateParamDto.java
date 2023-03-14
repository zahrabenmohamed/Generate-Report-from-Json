package com.example.Editique.dto;
import lombok.Data;


@Data
public class TemplateParamDto {

    private int id;
    private String name;
    private String description;
    private String type;
    private String selector;
    private String selectorType;
    private String flow;
}
