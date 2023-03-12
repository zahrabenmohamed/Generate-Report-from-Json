package com.example.Editique.service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

@Service
public class ReportService {

    public String exportReport(String reportFormat,String inputData) throws FileNotFoundException, JRException, JsonProcessingException {
        String path = "C:\\Users\\hp\\Desktop\\Report";

        //load file and compile it
        File file = ResourceUtils.getFile("classpath:rib.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        // Convert the user input (in JSON or XML format) into a Java object
        ObjectMapper mapper = new ObjectMapper();
        List<Map<String, Object>> data = mapper.readValue(inputData, new TypeReference<>() {});
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(Collections.singleton(data));
        // Set report parameters
        Map<String, Object> parameters = new HashMap<>();
        for (Map.Entry<String, Object> entry : data.get(0).entrySet()) {
            parameters.put(entry.getKey(), entry.getValue());
        }

        System.out.println("Input data: " + inputData);
        System.out.println("Parameters: " + parameters);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        if (reportFormat.equalsIgnoreCase("html")) {
            JasperExportManager.exportReportToHtmlFile(jasperPrint, path + "\\rib.html");
        }
        if (reportFormat.equalsIgnoreCase("pdf")) {
            JasperExportManager.exportReportToPdfFile(jasperPrint, path + "\\rib.pdf");
        }

        return "report generated in path : " + path;
    }


}
