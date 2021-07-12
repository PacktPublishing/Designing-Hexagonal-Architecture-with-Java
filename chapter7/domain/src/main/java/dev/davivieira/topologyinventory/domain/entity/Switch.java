package dev.davivieira.topologyinventory.domain.entity;

import dev.davivieira.topologyinventory.domain.specification.CIDRSpecification;
import dev.davivieira.topologyinventory.domain.specification.NetworkAmountSpec;
import dev.davivieira.topologyinventory.domain.specification.NetworkAvailabilitySpec;
import dev.davivieira.topologyinventory.domain.vo.*;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.function.Predicate;

@Getter
public class Switch extends Equipment {

    private SwitchType switchType;
    private List<Network> switchNetworks;

    @Builder
    public Switch(Id id, Vendor vendor, Model model, IP ip, Location location, SwitchType switchType, List<Network> switchNetworks){
        super(id, vendor, model, ip, location);
        this.switchType = switchType;
        this.switchNetworks = switchNetworks;
    }

    public static Predicate<Network> getNetworkProtocolPredicate(Protocol protocol){
        return s -> s.getNetworkAddress().getProtocol().equals(protocol);
    }

    public static Predicate<Switch> getSwitchTypePredicate(SwitchType switchType){
        return s -> s.switchType .equals(switchType);
    }

    public boolean addNetworkToSwitch(Network network) {
        var availabilitySpec = new NetworkAvailabilitySpec(
                network.getNetworkAddress(),
                network.getNetworkName(),
                network.getNetworkCidr());
        var cidrSpec = new CIDRSpecification();
        var amountSpec = new NetworkAmountSpec();

        if(cidrSpec.isSatisfiedBy(network.getNetworkCidr()))
            throw new IllegalArgumentException("CIDR is below "+CIDRSpecification.MINIMUM_ALLOWED_CIDR);

        if(!availabilitySpec.isSatisfiedBy(this))
            throw new IllegalArgumentException("This network already exists");

        if(!amountSpec.isSatisfiedBy(this)) {
            throw new IllegalArgumentException("The max number of networks is "+ NetworkAmountSpec.MAXIMUM_ALLOWED_NETWORKS);
        }
        return this.switchNetworks.add(network);
    }

    public boolean removeNetworkFromSwitch(Network network){
        return this.switchNetworks.remove(network);
    }
}
