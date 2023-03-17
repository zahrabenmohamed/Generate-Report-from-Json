package com.example.Editique.dto;
import com.example.Editique.constant.SelectorType;
import lombok.Data;


@Data
public class TemplateParamDto {

    private int id;
    private String name;
    private String description;
    private String type;
    private String selector;

    private SelectorType selectorType;
    private String source; // name of the flux ex:customer , transactions
}
