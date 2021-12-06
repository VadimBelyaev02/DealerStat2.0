package com.leverx.dealerstat.dto;

import lombok.*;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO extends BaseDTO {

    private String message;
    private Double rate;
    private Long userId;
    private Long authorId;
    private Date createdAt;
}
