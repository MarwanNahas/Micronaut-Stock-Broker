package com.udemy4.broker.data;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.javafaker.Faker;
import com.udemy4.broker.Symbol;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Singleton;

@Singleton
public class InMemoryStore {
    //Map in which the key is type string and the value is of type Symbol
    private final Map<String,Symbol> symbols = new HashMap<>();

    //to use the faker library we need to add a new faker object
    private final Faker faker = new Faker();

    //add logger
    private static final Logger LOG = LoggerFactory.getLogger(InMemoryStore.class);

    //The PostConstruct annotation is used on a method that needs to be executed after dependency injection is done to perform any initialization. This method must be invoked before the class is put into service. This annotation must be supported on all classes that support dependency injection. The method annotated with PostConstruct must be invoked even if the class does not request any resources to be injected.
    @PostConstruct
    public void initialize(){
        //we gonna fill up the symbols inside the Hash map using an Intstream from zero to 10 
        //IntStream.range basically works as a for loop then for each is the logic inside the for loop
        //3shan lw zawedt fel mosta2bal maybawazleesh 7aga fel testing ha3mel function tanya initializeWith w heya deh ely ha7ot feeha el  logic bs fo2eeh ha7ot eno ye clear el map 
       initializeWith(10);
    }

    public void initializeWith(int numberOfEntries){
        symbols.clear();
        IntStream.range(0, numberOfEntries).forEach(i ->
            addNewSymbol());
    }

    private void addNewSymbol(){
        //we gonna get the fake data from a package from git hub called java-faker we gonna add its deppendency in the pom.xml file 
        //the faker function willl now provide us with four random stock symbol from the nsdq stock exchange
       Symbol symbol = new Symbol(faker.stock().nsdqSymbol());
       //added the new symbol to the symbols hash map with key is the value of the symbol and value as the object symbol itself
       symbols.put(symbol.getValue(), symbol);
       LOG.debug("Added Symbol {}",symbol);

    }
    //to get the HashMap and everything in it
    public Map<String, Symbol> getSymbols() {
        return symbols;
    }

    //get Symbol by Value 
    public Symbol getSymbolByValue(String value){
        return symbols.get(value);
    }

    

}
