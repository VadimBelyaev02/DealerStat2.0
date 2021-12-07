package com.leverx.dealerstat.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
public class CommentDTO {

    private Long id;
    private String message;
    private Double rate;
    private UserDTO user;
    private UserDTO authorId;
    private LocalDate createdAt;
    private boolean approved;
}
