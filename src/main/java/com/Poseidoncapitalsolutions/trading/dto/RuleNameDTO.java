package com.poseidoncapitalsolutions.trading.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RuleNameDTO {

    private int id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Json is required")
    private String json;

    @NotBlank(message = "Template is required")
    private String template;

    @NotBlank(message = "SQL string is required")
    private String sqlStr;

    @NotBlank(message = "SQL part is required")
    private String sqlPart;

}
