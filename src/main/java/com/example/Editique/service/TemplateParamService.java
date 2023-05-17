package com.example.Editique.service;


import com.example.Editique.dto.TemplateParamDto;
import com.example.Editique.entity.Template;
import com.example.Editique.entity.TemplateParam;
import com.example.Editique.repository.TemplateParamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TemplateParamService {
    private final TemplateParamRepository templateParamRepository;

    @Autowired
    public TemplateParamService(TemplateParamRepository templateParamRepository) {
        this.templateParamRepository = templateParamRepository;
    }

    public TemplateParam saveTemplateParam(TemplateParam templateParam) {
        return templateParamRepository.save(templateParam);
    }






}
