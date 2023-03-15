package com.example.Editique.controller;

import com.example.Editique.dto.TemplateDto;
import com.example.Editique.entity.Template;
import com.example.Editique.service.TemplateService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TemplateController {

    @Autowired
    private TemplateService templateService;

    private

    @PostMapping(value = "/generate-template")
    ResponseEntity<ByteArrayResource> generateReport(@RequestBody TemplateDto templateDto) {
        try {
            byte[] report = templateService.generateTemplate(templateDto);
            ByteArrayResource resource = new ByteArrayResource(report);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=report.pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .contentLength(report.length)
                    .body(resource);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }











    }

