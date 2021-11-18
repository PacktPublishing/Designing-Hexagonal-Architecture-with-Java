package dev.davivieira.domain.specification;

import dev.davivieira.domain.entity.Router;
import dev.davivieira.domain.specification.shared.AbstractSpecification;
import dev.davivieira.domain.vo.RouterType;

public class RouterTypeSpecification extends AbstractSpecification<Router> {
    @Override
    public boolean isSatisfiedBy(Router router) {
        return router.isType(RouterType.EDGE);
    }
}
