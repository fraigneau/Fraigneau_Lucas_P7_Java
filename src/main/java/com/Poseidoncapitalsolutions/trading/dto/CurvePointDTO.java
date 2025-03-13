package com.poseidoncapitalsolutions.trading.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CurvePointDTO {

    private int id;

    @NotNull(message = "Term is required")
    @Positive(message = "Buy quantity must be a positive number")
    @Digits(integer = 10, fraction = 2, message = "Term must be a number with a maximum of 10 digits before the decimal point and 2 after")
    private double term;

    @NotNull(message = "Value is required")
    @Positive(message = "Value must be a positive number")
    @Digits(integer = 10, fraction = 2, message = "Value must be a number with a maximum of 10 digits before the decimal point and 2 after")
    private double value;

}
