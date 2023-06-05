package com.example.Editique.service;


import com.example.Editique.dto.TemplateParamDto;
import com.example.Editique.entity.Template;
import com.example.Editique.entity.TemplateParam;
import com.example.Editique.repository.TemplateParamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

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

    public TemplateParamDto getTemplateParam(Long id) {
        TemplateParam templateParam = templateParamRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("TemplateParam not found with id: " + id));
        return mapTemplateParamToDTO(templateParam);
    }

    public void deleteTemplateParam(Long id) {
        templateParamRepository.deleteById(id);
    }

    private TemplateParamDto mapTemplateParamToDTO(TemplateParam templateParam) {
        TemplateParamDto templateParamDTO = new TemplateParamDto();
        templateParamDTO.setId(templateParam.getId());
        templateParamDTO.setName(templateParam.getName());
        templateParamDTO.setDescription(templateParam.getDescription());
        templateParamDTO.setType(templateParam.getType());
        templateParamDTO.setSelectorType(templateParamDTO.getSelectorType());
        templateParamDTO.setSource(templateParamDTO.getSource());
        return templateParamDTO;
    }

    public TemplateParamDto updateTemplateParam(Long id, TemplateParamDto templateParamDTO) {
        TemplateParam templateParam = templateParamRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("TemplateParam not found with id: " + id));
        templateParam.setName(templateParamDTO.getName());
        templateParam.setSource(templateParamDTO.getSource());
        templateParam.setSelector(templateParamDTO.getSelector());
        templateParam.setSelectorType(templateParamDTO.getSelectorType());
        templateParam.setId(templateParam.getId());
        TemplateParam updatedTemplateParam = templateParamRepository.save(templateParam);
        return mapTemplateParamToDTO(updatedTemplateParam);
    }






}
