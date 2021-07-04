package dev.davivieira.topologyinventory.domain.specification;

import dev.davivieira.topologyinventory.domain.entity.Equipment;
import dev.davivieira.topologyinventory.domain.entity.Router;
import dev.davivieira.topologyinventory.domain.specification.shared.AbstractSpecification;
import dev.davivieira.topologyinventory.domain.vo.RouterType;

public class RouterTypeSpec extends AbstractSpecification<Equipment> {
    @Override
    public boolean isSatisfiedBy(Equipment router) {

        return ((Router)router).getRouterType().equals(RouterType.EDGE)
                || ((Router)router).getRouterType().equals(RouterType.CORE);
    }
}
