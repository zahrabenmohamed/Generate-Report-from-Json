package com.example.Editique.service;

import com.example.Editique.dto.GenerationRequest;
import com.example.Editique.entity.Template;
import com.example.Editique.entity.TemplateParam;
import com.example.Editique.repository.TemplateParamRepository;
import com.example.Editique.repository.TemplateRepository;
import com.jayway.jsonpath.JsonPath;
import lombok.SneakyThrows;
import net.minidev.json.JSONArray;
import net.sf.jasperreports.engine.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

@Service
public class TemplateService {

    private final TemplateRepository templateRepository;


    @Autowired
    public TemplateService(TemplateRepository templateRepository, TemplateParamRepository templateParamRepository) {
        this.templateRepository = templateRepository;
    }

    public List<Template> getTemplate(){
        return templateRepository.findAll(); //return a list
    }

    public Template getTemplateById(Long id){
        return templateRepository.findById(id).get();
    }

    public void deleteTemplate(Long id){
        templateRepository.deleteById(id);

    }
    public Template updateTemplate(Long id, Template updatedTemplate) {
        Optional<Template> optionalTemplate = templateRepository.findById(id);
        if (optionalTemplate.isPresent()) {
            Template template = optionalTemplate.get();
            template.setTemplateParam(updatedTemplate.getTemplateParam());
            template.setDescription(updatedTemplate.getDescription());
            template.setCode(updatedTemplate.getCode());
            template.setPath(updatedTemplate.getCode());
            return templateRepository.save(template);
        } else {
            throw new RuntimeException("Template not found with id: " + id);
        }
    }

    public byte[] generateTemplate(GenerationRequest request) throws FileNotFoundException, JRException {
        String FolderPath = "C:\\Users\\hp\\Desktop\\Report";
        Template template = templateRepository.findByCode(request.getTemplate())
                .orElseThrow(() -> new RuntimeException("Template not found"));
        String path=template.getPath();
        File file = ResourceUtils.getFile("src/main/resources/"+path);
        JasperReport jasperReport = JasperCompileManager.compileReport(String.valueOf(file));
        Map<String, Object> params = new HashMap<>();
        Object param = null;
        String flux = null;
        for (TemplateParam p : template.getTemplateParam()) {
            param = null;
            flux = request.getFlux().stream().filter(f -> f.getName().equals(p.getSource()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException(""))
                    .getValue();
            switch (p.getSelectorType()) {
                case JSON:
                    param = getJsonValue(flux, p.getSelector());
                    break;
                case XML:
                default:
            }
            params.put(p.getName(), param);
        }
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, new JREmptyDataSource());
        byte[] pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);
        String outputPath = FolderPath + "\\"+template.getCode()+".pdf";
        JasperExportManager.exportReportToPdfFile(jasperPrint, outputPath);
        return pdfBytes;
    }

    @SneakyThrows
    public static Object getJsonValue(String jsonString, String jsonPath) {
        Object jsonResult = JsonPath.read(jsonString, jsonPath);
        if (jsonResult instanceof JSONArray) {
            if (!((JSONArray) jsonResult).isEmpty()) {
                String value = "";
                for (Object o : ((JSONArray) jsonResult)) {
                    value += o.toString();
                }
                jsonResult = value;
            } else {
                return null;
            }
        }
        return jsonResult;
    }
}

