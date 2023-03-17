package com.example.Editique.controller;

import com.example.Editique.dto.GenerationRequest;
import com.example.Editique.service.TemplateService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;


@RestController
public class TemplateController {

    @Autowired
    private TemplateService templateService;

    private

    @PostMapping(value = "/generate-template")
    byte[] generateReport(@RequestBody GenerationRequest templateDto) {
            return templateService.generateTemplate(templateDto);
        }

    }

