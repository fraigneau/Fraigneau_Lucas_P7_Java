package com.poseidoncapitalsolutions.trading.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class TradeDTO {

    private int id;

    @NotBlank(message = "Account is required")
    private String account;

    @NotBlank(message = "Type is required")
    private String type;

    @NotNull(message = "Buy quantity is required")
    @Positive(message = "Buy quantity must be a positive number")
    @Digits(integer = 10, fraction = 2, message = "Buy quantity must be a number with a maximum of 10 digits before the decimal point and 2 after")
    private Double buyQuantity;

}
