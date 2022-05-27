package com.arkon.pipeline.v1.client;

import com.arkon.pipeline.v1.dto.Template;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "ad360a0e-b42f-482c-af12-1fd72140032e", url = "https://datos.cdmx.gob.mx")
public interface ApiClient {
    @GetMapping("/api/3/action/datastore_search?resource_id=ad360a0e-b42f-482c-af12-1fd72140032e&limit=207")
    Template get();
}
