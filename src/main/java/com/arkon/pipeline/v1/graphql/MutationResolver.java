package com.arkon.pipeline.v1.graphql;

import com.arkon.pipeline.v1.dto.Template;
import com.arkon.pipeline.v1.model.Information;
import com.arkon.pipeline.v1.repository.AlcaldiaRepository;
import com.arkon.pipeline.v1.repository.InformationRepository;
import com.arkon.pipeline.v1.services.DataServices;
import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MutationResolver implements GraphQLMutationResolver {

}
