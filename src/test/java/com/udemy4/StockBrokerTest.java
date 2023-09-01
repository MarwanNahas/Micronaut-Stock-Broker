package com.udemy4;

import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.json.tree.JsonNode;
import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;

import jakarta.inject.Inject;

@MicronautTest
class StockBrokerTest {

    @Inject
    EmbeddedApplication<?> application;

    @Test
    void testItWorks() {
        Assertions.assertTrue(application.isRunning());
    }
    
    @Inject
    @Client("/symbols")
    HttpClient client;

    @Test
    void symbolsEndpointReturnsListOfSymbols(){
        //exchange takes 2 parameters , the first one is the path to the HTTP end point (will be / as we defined the path already when we defined the client), and the second one is the content type which we are expecting as response
        var response = client.toBlocking().exchange("/", JsonNode.class);
        assertEquals(HttpStatus.OK,response.getStatus());
        assertEquals(10,response.getBody().get().size());
        
    }

}
