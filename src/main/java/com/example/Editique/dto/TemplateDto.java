package com.example.Editique.dto;
import com.example.Editique.entity.TemplateParam;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TemplateDto {


        private Long id;
        private String code;
        private String description;
        private String path;

        private List<TemplateParamDto> templateParam;




}
