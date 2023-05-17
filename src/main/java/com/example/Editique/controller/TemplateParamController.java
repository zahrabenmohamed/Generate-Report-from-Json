package com.example.Editique.controller;


import com.example.Editique.dto.TemplateParamDto;
import com.example.Editique.entity.TemplateParam;
import com.example.Editique.service.TemplateParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/template-params")
public class TemplateParamController {

    private final TemplateParamService templateParamService;

    @Autowired
    public TemplateParamController(TemplateParamService templateParamService) {
        this.templateParamService = templateParamService;
    }

    @PostMapping
    public ResponseEntity<TemplateParam> createTemplateParam(@RequestBody TemplateParam templateParam) {
        TemplateParam savedTemplateParam = templateParamService.saveTemplateParam(templateParam);
        return new ResponseEntity<>(savedTemplateParam, HttpStatus.CREATED);
    }
}
