package dev.davivieira.topologyinventory.domain.specification.shared;

import dev.davivieira.topologyinventory.domain.exception.GenericSpecificationException;

public abstract class AbstractSpecification<T> implements Specification<T> {

    public abstract void check(T t) throws GenericSpecificationException;

    public Specification<T> and(final Specification<T> specification) {
        return new AndSpecification<>(this, specification);
    }
}
