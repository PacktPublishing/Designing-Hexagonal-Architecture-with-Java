package dev.davivieira.topologyinventory.domain.specification;

import dev.davivieira.topologyinventory.domain.entity.Equipment;
import dev.davivieira.topologyinventory.domain.entity.Switch;
import dev.davivieira.topologyinventory.domain.specification.shared.AbstractSpecification;

public class NetworkAmountSpec extends AbstractSpecification<Equipment> {

    final static public int MAXIMUM_ALLOWED_NETWORKS = 6;

    @Override
    public boolean isSatisfiedBy(Equipment switchNetwork) {
        return ((Switch)switchNetwork).getSwitchNetworks().size()
                <=MAXIMUM_ALLOWED_NETWORKS;
    }
}
