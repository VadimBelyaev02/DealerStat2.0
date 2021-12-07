package com.leverx.dealerstat.dto;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {

    @NotBlank
    private Long id;

    @NotBlank
    private String message;

    @NotBlank
    @Min(0)
    @Max(5)
    private Double rate;

    @NotBlank
    private Long userId;

    @NotBlank
    private Long authorId;

    @NotBlank
    private LocalDate createdAt;

    @NotBlank
    private boolean approved;
}
