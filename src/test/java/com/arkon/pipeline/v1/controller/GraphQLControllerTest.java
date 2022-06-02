package com.arkon.pipeline.v1.controller;

import com.arkon.pipeline.v1.services.InformacionService;
import graphql.GraphQL;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@WebMvcTest(GraphQLController.class)
public class GraphQLControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GraphQL graphQL;

    @Test
    void verTodos() {
    }
}