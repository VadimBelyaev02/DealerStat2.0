package com.leverx.dealerstat.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {

    private Long id;
    private String message;
    private Double rate;
    private Long userId;
    private Long authorId;
    private LocalDate createdAt;
    private boolean approved;
}
