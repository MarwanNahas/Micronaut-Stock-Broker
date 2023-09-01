package com.udemy4.broker;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable

public class Symbol {
    String value;

    public Symbol(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
    
}
