package com.arkon.pipeline.v1.graphql;

import com.arkon.pipeline.v1.model.Alcaldia;
import com.arkon.pipeline.v1.model.Information;
import com.arkon.pipeline.v1.services.DataServices;
import graphql.schema.DataFetcher;
import graphql.schema.idl.RuntimeWiring;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GraphQLRegister {
    @Autowired
    private DataServices dataServices;
    @Autowired private QueryResolver queryResolver;

    public RuntimeWiring buildWiring() {
        DataFetcher<List<Information>> records = data -> {
            return (List<Information>) this.dataServices.records();
        };
        DataFetcher<List<Alcaldia>> alcaldias = data -> {
            return (List<Alcaldia>) this.queryResolver.alcaldiasDisponibles()   ;
        };
        DataFetcher<List<Information>> unidadesDisponibles = data -> {
            return (List<Information>) this.queryResolver.unidadesDisponibles()   ;
        };
        DataFetcher<Information> buscarPorId = data -> {
            return (Information) this.queryResolver.buscarPorId(data.getArgument("idVehiculo"))   ;
        };

        DataFetcher<List<Information>> buscarPorAlcaldia = data -> {
            return (List<Information>) this.queryResolver.buscarPorAlcaldia(data.getArgument("alcaldia"))   ;
        };

        return RuntimeWiring.newRuntimeWiring().type("Query",
                        typeWriting ->
                                typeWriting
                                        .dataFetcher("records", records)
                                        .dataFetcher("alcaldiasDisponibles", alcaldias)
                                        .dataFetcher("unidadesDisponibles", unidadesDisponibles)
                                        .dataFetcher("buscarPorId", buscarPorId)
                                        .dataFetcher("buscarPorAlcaldia", buscarPorAlcaldia)
                )
                .build();

    }
}
