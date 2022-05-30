package com.arkon.pipeline.v1.dto.maps;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GoogleMaps {
    private PlusCode plus_code;
    private List<Results> results;
    private String status;
}
