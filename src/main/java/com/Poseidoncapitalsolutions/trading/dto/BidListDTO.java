package com.poseidoncapitalsolutions.trading.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BidListDTO {

    private int id;

    @NotBlank(message = "Account cannot be empty")
    private String account;

    @NotBlank(message = "Type cannot be empty")
    private String type;

    @NotNull(message = "Bid quantity is required")
    @Digits(integer = 10, fraction = 2, message = "Bid quantity must be a number with a maximum of 10 digits before the decimal point and 2 after")
    private Double bidQuantity;
}
