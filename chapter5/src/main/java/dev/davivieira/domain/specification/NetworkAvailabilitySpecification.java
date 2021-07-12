package dev.davivieira.domain.specification;

import dev.davivieira.domain.entity.Router;
import dev.davivieira.domain.specification.shared.AbstractSpecification;
import dev.davivieira.domain.vo.IP;
import dev.davivieira.domain.vo.Network;

public class NetworkAvailabilitySpecification extends AbstractSpecification<Router> {

    private IP address;
    private String name;
    private int cidr;

    public NetworkAvailabilitySpecification(IP address, String name, int cidr) {
        this.address = address;
        this.name = name;
        this.cidr = cidr;
    }

    @Override
    public boolean isSatisfiedBy(Router router) {
        return router!=null && isNetworkAvailable(router);
    }

    private boolean isNetworkAvailable(Router router){
        var availability = true;
        for (Network network : router.getNetworkSwitch().getNetworks()) {
            if(network.getAddress().getIPAddress().equals(address.getIPAddress()) && network.getCidr() == cidr) {
                availability = false;
                break;
            }
        }
        return availability;
    }
}
