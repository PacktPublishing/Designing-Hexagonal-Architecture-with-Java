package dev.davivieira.topologyinventory.framework.adapters.input.rest.converter;

import dev.davivieira.topologyinventory.domain.valueobject.Id;

import javax.inject.Singleton;
import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

@Provider
@Singleton
public class IdParamConverterProvider implements ParamConverterProvider {

    @SuppressWarnings("unchecked")
    @Override
    public <T> ParamConverter<T> getConverter(Class<T> rawType,
                                              Type genericType,
                                              Annotation[] annotations) {
        if (rawType.isAssignableFrom(Id.class)) {
            return (ParamConverter<T>) new IdParamConverter();
        }
        return null;
    }
}
