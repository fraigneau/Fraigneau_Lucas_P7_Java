package com.poseidoncapitalsolutions.trading.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class RatingDTO {

    private int id;

    @NotBlank(message = "Moody's rating cannot be empty")
    private String moodysRating;

    @NotBlank(message = "S&P rating cannot be empty")
    private String sandPRating;

    @NotBlank(message = "Fitch rating cannot be empty")
    private String fitchRating;

    @NotNull(message = "Order number is required")
    @Positive(message = "Order number must be positive")
    private int orderNumber;

}
