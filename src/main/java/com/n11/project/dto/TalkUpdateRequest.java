package com.n11.project.dto;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TalkUpdateRequest {
    @NotBlank(message = "Title cannot be blank")
    @NotNull(message = "Title cannot be null")
    private String title;

    @NotNull(message = "Duration cannot be null")
    @Max(value = 240, message = "Duration cannot be more than 240 minutes")
    @Min(value = 5, message = "Duration cannot be smaller than 5 minutes")
    private Integer duration;

    @NotBlank(message = "NewTitle cannot be blank")
    private String newTitle;

    @NotNull(message = "NewDuration cannot be null")
    @Max(value = 240, message = "NewDuration cannot be more than 240 minutes")
    @Min(value = 5, message = "NewDuration cannot be smaller than 5 minutes")
    private Integer newDuration;
}
