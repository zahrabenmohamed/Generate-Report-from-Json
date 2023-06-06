package com.example.Editique.controller;

import com.example.Editique.dto.GenerationRequest;
import com.example.Editique.dto.TemplateDto;
import com.example.Editique.entity.Template;
import com.example.Editique.repository.TemplateRepository;
import com.example.Editique.service.TemplateService;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;


@RestController
public class TemplateController {

    @Autowired
    private TemplateService templateService;

    @Autowired
    private TemplateRepository templateRepository;

    @GetMapping("/templates")
    public List<Template> getTemplate(){
        return  templateService.getTemplate();
    }

    /**************** this will add template in the database *************************/

    @PostMapping("/addtemplate")
    public ResponseEntity<?> setTemplate(@RequestParam("file") MultipartFile file, @RequestParam("data") String t) throws Exception {
        Gson g = new Gson();
        TemplateDto template = g.fromJson(t, TemplateDto.class);

        // Set the path value as the name of the uploaded file
        String fileName = file.getOriginalFilename();
        template.setPath(fileName);

        templateService.saveTemplate(template, fileName); // Pass the file name to the service method

        try {
            //todo modify this to not be realtivePath
            String relativePath = "C:/Users/hp/Desktop/Project/Generate-Report-from-Json/src/main/resources/" + fileName;
            System.out.println(relativePath);
            file.transferTo(new File(relativePath));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.ok("File uploaded successfully.");
    }


    /********************************************************************************/

    @GetMapping(value = "template/{id}")
    public ResponseEntity<?> getTemplateById(@PathVariable Long id){
        return ResponseEntity.ok(templateService.getTemplateById(id));

    }

    @GetMapping("/{code}")
    public ResponseEntity<Template> getTemplateByCode(@PathVariable String code) {
        Template template = templateRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Template not found"));

        return ResponseEntity.ok(template);
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

