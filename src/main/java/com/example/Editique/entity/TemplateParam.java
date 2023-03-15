package com.example.Editique.entity;

import com.example.Editique.constant.SelectorType;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class TemplateParam {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private String description;
    private String type;
    private String selector;

    private SelectorType selectorType;
    private String source;
}
