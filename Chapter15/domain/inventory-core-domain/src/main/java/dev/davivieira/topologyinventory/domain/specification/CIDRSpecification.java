package dev.davivieira.topologyinventory.domain.specification;

import dev.davivieira.topologyinventory.domain.exception.GenericSpecificationException;
import dev.davivieira.topologyinventory.domain.specification.shared.AbstractSpecification;

public final class CIDRSpecification extends AbstractSpecification<Integer> {

    private static final int MINIMUM_ALLOWED_CIDR = 8;

    @Override
    public boolean isSatisfiedBy(Integer cidr) {
        return cidr >= MINIMUM_ALLOWED_CIDR;
    }

    @Override
    public void check(Integer cidr) throws GenericSpecificationException {
        if (!isSatisfiedBy(cidr)) {
            throw new GenericSpecificationException("CIDR is below " + CIDRSpecification.MINIMUM_ALLOWED_CIDR);
        }

    }
}
