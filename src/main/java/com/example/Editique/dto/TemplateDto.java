package com.example.Editique.dto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TemplateDto {


        private Long id;
        private String code;
        private String description;
        private String path;

        private TemplateParamDto templateParam;


}
