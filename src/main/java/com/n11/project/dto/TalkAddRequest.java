package com.n11.project.dto;


import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TalkAddRequest {
    @NotNull(message = "Talks cannot be null")
    @Valid
    List<TalkItem> talks;

    @Data
    @AllArgsConstructor
    public static class TalkItem {
        @NotNull(message = "Title cannot be null")
        @NotBlank(message = "Title cannot be blank")
        private String title;

        @NotNull(message = "Duration cannot be null")
        @Max(value = 240, message = "Duration cannot be more than 240 minutes")
        @Min(value = 5, message = "Duration cannot be smaller 5 minutes")
        private Integer duration;
    }
}
