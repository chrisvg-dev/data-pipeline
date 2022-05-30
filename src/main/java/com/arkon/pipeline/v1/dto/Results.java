package com.arkon.pipeline.v1.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
/**
 * La clase GoogleResults apunta a la llave del JSON recibido donde se encuentran los registros, los cuales se representan
 * en otra clase.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Results {
    private boolean include_total;
    private Integer limit;
    private String records_format;
    private String resource_id;
    private String total_estimation_threshold;
    private ArrayList<Record> records;
}
