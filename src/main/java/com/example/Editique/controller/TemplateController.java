package com.example.Editique.controller;

import com.example.Editique.dto.GenerationRequest;
import com.example.Editique.entity.Template;
import com.example.Editique.service.TemplateService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class TemplateController {

    @Autowired
    private TemplateService templateService;

    @GetMapping("/templates")
    public List<Template> getTemplate(){
        return  templateService.getTemplate();
    }



    @GetMapping(value = "template/{id}")
    public ResponseEntity<?> getTemplateById(@PathVariable Long id){
        return ResponseEntity.ok(templateService.getTemplateById(id));

    }

    @DeleteMapping(value = "/template-delete/{id}")
    public void deleteTemplateById(@PathVariable Long id){
        templateService.deleteTemplate(id);
    }

    @PutMapping(value = "/template-update/{id}")
    public ResponseEntity<Template> updateTemplate(@PathVariable("id") long id, @RequestBody Template template) {
        Template updatedTemplate = templateService.updateTemplate(id, template);
        return ResponseEntity.ok(updatedTemplate);
    }



    @PostMapping(value = "/generate-template")
    byte[] generateReport(@RequestBody GenerationRequest request) throws Exception{
            return templateService.generateTemplate(request);
        }

    }

