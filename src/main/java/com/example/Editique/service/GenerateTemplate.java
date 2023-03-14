package com.example.Editique.service;
import com.example.Editique.entity.TemplateParam;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

@Service
public class GenerateTemplate {
    public byte[] generateReport( TemplateParam[] params) throws JRException, FileNotFoundException, JsonProcessingException {
        // Load the JasperReports template file

        File file = ResourceUtils.getFile("classpath:Releve.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(params);
        List<Map<String, Object>> data = mapper.readValue(jsonString, new TypeReference<List<Map<String, Object>>>() {});
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(Collections.singleton(data));
        // Set report parameters
        Map<String, Object> parameters = new HashMap<>();
        /*for (TemplateParam param : params) {
            parameters.put("param"+params.length,param.getName());
        }


        System.out.println("Input data: " + params);
        System.out.println("Parameters: " + parameters);*/
        for (int i = 0; i < params.length; i++) {
            String paramName = "param" + (i + 1);
            String paramValue = params[i].getName();
            parameters.put(paramName, paramValue);
        }

        System.out.println("Parameters: " + parameters);


        // Generate the report
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
        return outputStream.toByteArray();
    }
}
