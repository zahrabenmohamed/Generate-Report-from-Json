package com.example.Editique.entity;

import lombok.Data;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Template{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String code;
    private String description;
    private String path;

    @OneToMany()
    @NotFound()
    private List<TemplateParam> templateParam;

}
