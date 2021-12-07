package com.leverx.dealerstat.dto;

import lombok.*;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
public class CommentDTO {

    private Long id;
    private String message;
    private Double rate;
    private Long userId;
    private Long authorId;
    private Date createdAt;
    private boolean approved;
}
