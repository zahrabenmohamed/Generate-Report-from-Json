package com.example.Editique.controller;


import com.example.Editique.dto.TemplateParamDto;
import com.example.Editique.entity.TemplateParam;
import com.example.Editique.service.TemplateParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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



    @GetMapping("/{id}")
    public ResponseEntity<TemplateParamDto> getTemplateParam(@PathVariable Long id) {
        TemplateParamDto templateParamDto = templateParamService.getTemplateParam(id);
        return ResponseEntity.ok(templateParamDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TemplateParamDto> updateTemplateParam(
            @PathVariable Long id, @RequestBody TemplateParamDto templateParamDTO) {
        TemplateParamDto updatedTemplateParam = templateParamService.updateTemplateParam(id, templateParamDTO);
        return ResponseEntity.ok(updatedTemplateParam);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTemplateParam(@PathVariable Long id) {
        templateParamService.deleteTemplateParam(id);
        return ResponseEntity.noContent().build();
    }
}
