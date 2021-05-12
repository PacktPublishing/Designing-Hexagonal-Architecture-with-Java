package dev.davivieira.domain.service;

import dev.davivieira.domain.entity.Router;
import dev.davivieira.domain.specification.CIDRSpecification;
import dev.davivieira.domain.specification.NetworkAmountSpecification;
import dev.davivieira.domain.specification.NetworkAvailabilitySpecification;
import dev.davivieira.domain.specification.RouterTypeSpecification;
import dev.davivieira.domain.vo.IP;
import dev.davivieira.domain.vo.Network;

public class NetworkOperation {

    public static Router createNewNetwork(Router router, Network network) {
        var availabilitySpec = new NetworkAvailabilitySpecification(network.getAddress(), network.getName(), network.getCidr());
        var cidrSpec = new CIDRSpecification();
        var routerTypeSpec = new RouterTypeSpecification();
        var amountSpec = new NetworkAmountSpecification();

        if(cidrSpec.isSatisfiedBy(network.getCidr()))
            throw new IllegalArgumentException("CIDR is below "+CIDRSpecification.MINIMUM_ALLOWED_CIDR);

        if(availabilitySpec.isSatisfiedBy(router))
            throw new IllegalArgumentException("Address already exist");

        if(amountSpec.and(routerTypeSpec).isSatisfiedBy(router)) {
            Network newNetwork = router.createNetwork(network.getAddress(), network.getName(), network.getCidr());
            router.addNetworkToSwitch(newNetwork);
        }
        return router;
    }
}
