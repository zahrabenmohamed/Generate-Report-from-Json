package com.example.Editique.service;
import net.sf.jasperreports.engine.*;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

@Service
public class GenerateReport {
    public byte[] generateReport(String templateName, String selector) throws JRException, JRException, FileNotFoundException {
        // Load the JasperReports template file
        File file = ResourceUtils.getFile("classpath:"+templateName+".jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

        // Set the report parameters
        Map<String, Object> parameters = new HashMap<>();
        System.out.println(selector);
        parameters.put("selector", selector);

        // Generate the report
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

        return outputStream.toByteArray();
    }
}
