package com.example.Editique.controller;
import com.example.Editique.entity.Template;
import com.example.Editique.entity.TemplateParam;
import com.example.Editique.service.GenerateRelve;
import com.example.Editique.service.GenerateReport;
import com.example.Editique.service.ReportService;
import com.fasterxml.jackson.core.JsonProcessingException;
import net.sf.jasperreports.engine.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;

@RestController
public class ReportController {

    @Autowired
    GenerateRelve generateRelve;

    @Autowired
    ReportService reportService;

    @Autowired
    GenerateReport generateReport;

    @GetMapping("/report/{format}")
    public String generateReport(@PathVariable String format, @RequestBody String inputData) throws JRException, FileNotFoundException, JsonProcessingException {
        return reportService.exportReport(format, inputData);
    }

    @GetMapping("/generate/{format}")
    public String generate(@PathVariable String format, @RequestBody String inputData) throws JRException, FileNotFoundException, JsonProcessingException {
        return generateRelve.export(format,inputData);
    }



    @PostMapping("/generate-template")
    public ResponseEntity<ByteArrayResource> generateReport(@RequestBody TemplateParam params) throws JRException, FileNotFoundException {
        byte[] reportBytes = generateReport.generateReport(params.getName(), params.getSelector());

        // Return the report as a downloadable file
        ByteArrayResource resource = new ByteArrayResource(reportBytes);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + params.getName() + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .contentLength(reportBytes.length)
                .body(resource);
    }

    @GetMapping("/templates")
    public Template getTempaltes(){
        return getTempaltes();
    }


}
