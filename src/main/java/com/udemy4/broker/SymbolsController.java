package com.udemy4.broker;

import java.util.ArrayList;
import java.util.List;

import com.udemy4.broker.data.InMemoryStore;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

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
    
}
