package com.example.Editique.service;

import aj.org.objectweb.asm.TypeReference;
import com.example.Editique.dto.GenerationRequest;
import com.example.Editique.dto.TemplateDto;
import com.example.Editique.entity.Template;
import com.example.Editique.entity.TemplateParam;
import com.example.Editique.repository.TemplateParamRepository;
import com.example.Editique.repository.TemplateRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import lombok.SneakyThrows;
import net.minidev.json.JSONArray;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
import org.apache.camel.util.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

@Service
public class TemplateService {

    private final TemplateRepository templateRepository;
    private final TemplateParamRepository templateParamRepository;


    @Autowired
    public TemplateService(TemplateRepository templateRepository, TemplateParamRepository templateParamRepository) {
        this.templateRepository = templateRepository;
        this.templateParamRepository = templateParamRepository;
    }

    public Optional<Template> findTemplateById(Long id){
        return Optional.ofNullable(templateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Template not found")));}


    public List<Template> findTemplateByName(String name) {
        return templateRepository.findByCode(name);
    }

    public byte[] generateTemplate(TemplateDto templateDto) throws FileNotFoundException, JRException, JsonProcessingException {
        //TODO--you should check the the path if it's not null
        System.out.println(templateDto);
        Map<String,String> data=new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        Object jsonObject = mapper.convertValue(templateDto.getTemplateParam(), Object.class);
        String jsonString = mapper.writeValueAsString(jsonObject);
        System.out.println(jsonString);
        String path=templateDto.getPath();
        JasperReport jasperReport = JasperCompileManager.compileReport(path);
        JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(Collections.singleton(jsonString));
        // Add parameters
        Map<String, Object> parameters = new HashMap<>();
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,
                jrBeanCollectionDataSource);
        JasperExportManager.exportReportToPdfFile(jasperPrint, templateDto.getCode()+".pdf");




        return new byte[0];
    }


    public byte[] generateTemplate(GenerationRequest request) {
        Template template = templateRepository.findByCode(request.getTemplate())
                .orElseThrow(() -> new RuntimeException("Template not found"));
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

        return null;


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
}
