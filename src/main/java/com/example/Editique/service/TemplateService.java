package com.example.Editique.service;

import com.example.Editique.dto.GenerationRequest;
import com.example.Editique.dto.TemplateDto;
import com.example.Editique.dto.TemplateParamDto;
import com.example.Editique.entity.Template;
import com.example.Editique.entity.TemplateParam;
import com.example.Editique.repository.TemplateParamRepository;
import com.example.Editique.repository.TemplateRepository;

import com.jayway.jsonpath.JsonPath;
import lombok.SneakyThrows;
import net.minidev.json.JSONArray;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TemplateService {

    private final TemplateRepository templateRepository;

    private final TemplateParamRepository templateParamRepository;


    @Autowired
    public TemplateService(TemplateRepository templateRepository, TemplateParamRepository templateParamRepository, TemplateParamRepository templateParamRepository1) {
        this.templateRepository = templateRepository;
        this.templateParamRepository = templateParamRepository1;
    }



    public List<Template> getTemplate(){
        return templateRepository.findAll(); //return a list
    }


          /*************************************************************************************/
          public void saveTemplate(TemplateDto templateDto ,String fileName) {
              System.out.println("he enter here , so error is happen here");
              Template template = new Template();
              template.setCode(templateDto.getCode());
              template.setDescription(templateDto.getCode());
              template.setPath(fileName);

              List<TemplateParam> templateParams = new ArrayList<>();
              for (TemplateParamDto paramDto : templateDto.getTemplateParam()) {
                  TemplateParam param = new TemplateParam();
                  param.setName(paramDto.getName());
                  param.setDescription(paramDto.getDescription());
                  param.setType(paramDto.getType());
                  param.setSelector(paramDto.getSelector());
                  param.setSelectorType(paramDto.getSelectorType());
                  param.setSource(paramDto.getSource());

                  templateParamRepository.save(param); // Save each TemplateParam entity

                  templateParams.add(param);
              }
              template.setTemplateParam(templateParams);

              templateRepository.save(template); // Save the Template entity
          }



    /****************************************************************************************/


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





    /**************************************GENERATION OF TEMPLATE ***********************************************/


    public byte[] generateTemplate(GenerationRequest request) throws IOException, JRException {
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
                    if (p.getType().equals("java.lang.String")) {
                        param = getJsonValue(flux, p.getSelector()).toString();
                    } else if (p.getType().equals("java.lang.Integer")) {
                        param = Integer.parseInt(getJsonValue(flux, p.getSelector()).toString());
                    } else if (p.getType().equals("java.lang.Float")) {
                        param = Float.parseFloat(getJsonValue(flux, p.getSelector()).toString());
                    } else if (p.getType().equals("JRbeanCollection")) {
                        System.out.println("*******************************");
                        param =  getJsonValue(flux, p.getSelector());
                        System.out.println("our param in jrbeanCollection case: "+param);
                        List<Map<String, Object>> list = (List<Map<String, Object>>) param;
                        System.out.println("this is our list "+list);
                        JRDataSource dataSource = new JRBeanCollectionDataSource(list, false);
                        System.out.println(dataSource);
                        System.out.println(dataSource.getClass());
                        System.out.println("*******************************");
                        param=dataSource;

                    } else {
                        throw new RuntimeException("Unsupported type: " + p.getType());
                    }
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
               List<Object> resultList = new ArrayList<>();
               for (Object o : ((JSONArray) jsonResult)) {
                   if (o instanceof LinkedHashMap) {
                       LinkedHashMap map = (LinkedHashMap) o;
                       resultList.add(map);
                   }
               }
               jsonResult = resultList;
           } else {
               return null;
           }
       }
       System.out.println(jsonResult);
       return jsonResult;
   }



}

