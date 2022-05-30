package com.arkon.pipeline.v1.controller;

import com.arkon.pipeline.v1.dto.AlcaldiaDto;
import com.arkon.pipeline.v1.dto.Template;
import com.arkon.pipeline.v1.model.Information;
import com.arkon.pipeline.v1.services.DataServices;
import graphql.GraphQL;
import graphql.language.TypeDefinition;
import graphql.schema.DataFetcher;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/pipeline")
@CrossOrigin(origins = "*")
public class Pipeline {
    public static final Logger log = LoggerFactory.getLogger(Pipeline.class);

    @Autowired private DataServices dataServices;

    @Value("classpath:schema.graphqls")
    private Resource resource;

    private GraphQL graphQL;

    @PostConstruct
    public void loadSchema() throws IOException {
        File schemaFile = resource.getFile();
        TypeDefinitionRegistry registry = new SchemaParser().parse(schemaFile);
        RuntimeWiring wiring = buildWiring();
        GraphQLSchema schema = new SchemaGenerator().makeExecutableSchema(registry, wiring);
        graphQL = GraphQL.newGraphQL(schema).build();
    }

    private RuntimeWiring buildWiring() {
        DataFetcher<List<Information>> fetcher1 = data -> {
            return (List<Information>) dataServices.records();
        };

        return RuntimeWiring.newRuntimeWiring().type("Query",
                        typeWriting -> typeWriting.dataFetcher("getAllPerson", fetcher1))
                .build();

    }

    @GetMapping
    public List<Information> get() {
        return this.dataServices.records();
    }

    @GetMapping("/alcaldias")
    public Set<AlcaldiaDto> alcaldiasDisponibles() {
        return this.dataServices.alcaldiasDisponibles();
    }

    @GetMapping("/alcaldia/{alcaldia}")
    public List<Information> unidadesPorAlcaldia(@PathVariable String alcaldia) {
        return this.dataServices.findByAlcaldia( alcaldia );
    }
    @GetMapping("/udisponibles")
    public List<Information> unidadesDisponibles() {
        return this.dataServices.unidadesDisponibles();
    }

    @GetMapping("/vehiculo/{id}")
    public Information ubicacionVehicle(@PathVariable Integer id) {
        return this.dataServices.buscarPorId(id);
    }

    @GetMapping("/recolectar")
    public boolean recolectar() {
        try {
            Template template = this.dataServices.stream();
            this.dataServices.persist(template);
            return true;
        }catch (Exception e)  {
            log.error( "Error a la hora de recolectar: " + e.getMessage() );
            return false;
        }
    }
}
