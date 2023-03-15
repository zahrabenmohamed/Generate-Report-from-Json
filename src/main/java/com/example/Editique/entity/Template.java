package com.example.Editique.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Template{


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String code; // name of template
    private String description;
    private String path; // path of jrxml file

    @OneToMany
    private List<TemplateParam> templateParam;

}
