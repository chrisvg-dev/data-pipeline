package com.arkon.pipeline.v1.dto.maps;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlusCode {
    private String compound_code;
    private String global_code;
}
