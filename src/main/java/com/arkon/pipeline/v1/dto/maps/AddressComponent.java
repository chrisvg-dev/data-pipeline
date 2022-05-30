package com.arkon.pipeline.v1.dto.maps;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressComponent {
    private String long_name;
    private String short_name;
    private String types[];
}