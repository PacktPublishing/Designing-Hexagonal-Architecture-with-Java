package dev.davivieira.topologyinventory.domain.specification.shared;

import dev.davivieira.topologyinventory.domain.entity.EdgeRouter;

public abstract class AbstractSpecification<T> implements Specification<T> {

    public abstract boolean isSatisfiedBy(T t);

    public Specification<T> and(final Specification<T> specification) {
        return new AndSpecification<T>(this, specification);
    }
}
