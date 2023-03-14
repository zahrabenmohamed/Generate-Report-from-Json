package com.example.Editique.dto;
import lombok.Data;

@Data
public class TemplateDto {


        private Long id;
        private String name;
        private String code;
        private String description;
        private String source;

        private TemplateParamDto templateParam;


}
