package com.udemy4.broker;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.udemy4.broker.data.InMemoryStore;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.QueryValue;

@Controller("/symbols")
public class SymbolsController {

    private InMemoryStore inMemoryStore; 
    

    public SymbolsController(InMemoryStore inMemoryStore) {
        this.inMemoryStore = inMemoryStore;
    }


    //what will appear to me is a JSON Array with multiple JSON Objects [{"value":"GWRS"},{"value":"AEIS"},{"value":"PCLN"},{"value":"EXPO"},{"value":"BMTC"},{"value":"ARRY"},{"value":"KOSS"},{"value":"MU"},{"value":"DGICA"},{"value":"EGHT"}]
    @Get
    public List<Symbol> getAll(){
        return new ArrayList<>(inMemoryStore.getSymbols().values());
    }

    //get by Value Learning @PathVariable
    @Get("{value}")
    public Symbol getSymbolByValue(@PathVariable String value){
        return inMemoryStore.getSymbolByValue(value);
    }
    
    //Learning Query Parameters u put them in curly braces and at the start put ? (We will have 2 query parameters here)
    //we put optional since if the parameter is not given put it as default value  ana esta5demt el Optional 3shan 2a2dar 2a2ool orElse(0)
    @Get("/filter{?max,offset}")
    public List<Symbol> getSymbols (@QueryValue Optional<Integer> max,@QueryValue Optional<Integer> offset){
        return 
        //this returns a list of symbols objects
        inMemoryStore.getSymbols().values()
        //Java streams enable functional-style operations on streams of elements. A stream is an abstraction of a non-mutable collection of functions applied in some order to the data. A stream is not a collection where you can store elements.
        //Java streams represent a pipeline through which data will flow and the functions to operate on the data. A pipeline in this instance consists of a stream source, followed by zero or more intermediate operations, and a terminal operation.
        .stream()
        //The skip(n) method is an intermediate operation that discards the first n elements of a stream
        .skip(offset.orElse(0))
        //The limit(n) method is another intermediate operation that returns a stream not longer than the requested size
        .limit(max.orElse(0))
        //change it to List
        .toList();
    }


}
