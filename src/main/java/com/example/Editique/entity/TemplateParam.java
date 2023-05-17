package com.example.Editique.entity;

import com.example.Editique.constant.SelectorType;
import lombok.Data;

import javax.persistence.*;

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

    @ManyToOne()
    @JoinColumn(name = "template_id")
    private  Template templateId;
    @Enumerated(EnumType.STRING)
    private SelectorType selectorType;
    private String source; //transaction s, customer ( name flux )
}

