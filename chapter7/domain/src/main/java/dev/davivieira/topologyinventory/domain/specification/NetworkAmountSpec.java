package dev.davivieira.topologyinventory.domain.specification;

import dev.davivieira.topologyinventory.domain.entity.Equipment;
import dev.davivieira.topologyinventory.domain.entity.Switch;
import dev.davivieira.topologyinventory.domain.exception.GenericSpecificationException;
import dev.davivieira.topologyinventory.domain.specification.shared.AbstractSpecification;

public class NetworkAmountSpec extends AbstractSpecification<Equipment> {

    final static public int MAXIMUM_ALLOWED_NETWORKS = 6;

    @Override
    public boolean isSatisfiedBy(Equipment switchNetwork) {
        return ((Switch)switchNetwork).getSwitchNetworks().size()
                <=MAXIMUM_ALLOWED_NETWORKS;
    }

    @Override
    public void check(Equipment equipment) throws GenericSpecificationException {
        if(!isSatisfiedBy(equipment))
            throw new GenericSpecificationException("The max number of networks is "+ NetworkAmountSpec.MAXIMUM_ALLOWED_NETWORKS);
    }
}
