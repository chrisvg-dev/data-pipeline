package com.arkon.pipeline.v1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseDto {
    private Boolean status;
    private String message;
}
