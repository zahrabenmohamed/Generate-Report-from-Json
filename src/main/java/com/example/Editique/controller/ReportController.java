package com.example.Editique.controller;

import com.example.Editique.service.ReportService;
import com.fasterxml.jackson.core.JsonProcessingException;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;

@RestController
public class ReportController {

    @Autowired
    ReportService reportService;

    @GetMapping("/report/{format}")
    public String generateReport(@PathVariable String format, @RequestBody String inputData) throws JRException, FileNotFoundException, JsonProcessingException {
        return reportService.exportReport(format, inputData);
    }
}
