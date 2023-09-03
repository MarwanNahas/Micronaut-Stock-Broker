package com.udemy4;

import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.json.tree.JsonNode;
import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.udemy4.broker.Symbol;
import com.udemy4.broker.data.InMemoryStore;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import jakarta.inject.Inject;

@MicronautTest
class StockBrokerTest {
    //so we can log anythign we want
    private static final Logger LOG = LoggerFactory.getLogger(StockBrokerTest.class);


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

    @Inject
    InMemoryStore inMemoryStore;

    //before each deh ma3naha en el method ely gowaha hateshta8al mara le kol test ya3ny hateshta8al abl symbolsEndpointReturnsListOfSymbols w 2abl symbolsEndpointReturnsTheCorrectSymbol w hakaza
    @BeforeEach
    void setup(){
        inMemoryStore.initializeWith(10);
    }

     @Test
    void symbolsEndpointReturnsTheCorrectSymbol(){
        var testSymbol = new Symbol("TEST");
        //delwa2ty hazawed el testSymbol 3ala el inMemoryStore 3shan 2at2aked eny hala2eeh
        inMemoryStore.getSymbols().put(testSymbol.getValue(), testSymbol);

        //keda el path b2a localhost:8090/symbols/TEST
        var response = client.toBlocking().exchange("/" + testSymbol.getValue(), Symbol.class);
        assertEquals(HttpStatus.OK,response.getStatus());
        assertEquals(testSymbol.getValue(),response.getBody().get().getValue());
        
    }


    @Test
    void symbolsEndpointReturnsListOfSymbolTakingQueryParametersIntoAccount(){
        //To verify that only 5 elemets will be returned at a max = 5
        var max5 = client.toBlocking().exchange("/filter?max=5", JsonNode.class);
        assertEquals(HttpStatus.OK,max5.getStatus());
        LOG.info("Max:5 {}",max5.getBody().get().getValue().toString());
        assertEquals(5,max5.getBody().get().size());
        //To verify offset
        var offset7 = client.toBlocking().exchange("/filter?max=5&offset=7", JsonNode.class);
        assertEquals(HttpStatus.OK,offset7.getStatus());
        LOG.info("Max:3 & offset:7 {}",offset7.getBody().get().getValue().toString());
        assertEquals(3,offset7.getBody().get().size());
    }
}


