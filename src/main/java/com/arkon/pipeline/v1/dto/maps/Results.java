package com.arkon.pipeline.v1.dto.maps;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Results {
    private List<AddressComponent> address_components;
    private String formatted_address;
}
