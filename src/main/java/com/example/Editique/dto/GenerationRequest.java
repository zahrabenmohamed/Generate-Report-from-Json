package com.example.Editique.dto;

import lombok.Data;

import java.util.List;

@Data
public class GenerationRequest {
    private String template;
    private List<FluxDto> flux;
}
