package com.arkon.pipeline.v1.graphql;

import com.arkon.pipeline.v1.model.Information;
import com.arkon.pipeline.v1.repository.InformationRepository;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AllInformationFetcher implements DataFetcher<List<Information>> {

    @Autowired private InformationRepository informationRepository;

    @Override
    public List<Information> get(DataFetchingEnvironment dataFetchingEnvironment) {
        return this.informationRepository.findAll();
    }
}
