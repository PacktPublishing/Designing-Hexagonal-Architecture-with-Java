package dev.davivieira.topologyinventory.framework.adapters.input.rest.converter;

import dev.davivieira.topologyinventory.domain.valueobject.Id;

import javax.ws.rs.ext.ParamConverter;

public class IdParamConverter implements ParamConverter<Id> {

    @Override
    public Id fromString(String value){
       return Id.withId(value);
    }

    @Override
    public String toString(Id id) {
        return id.getUuid().toString();
    }
}
