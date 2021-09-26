package dev.davivieira.topologyinventory.domain.entity;

import dev.davivieira.topologyinventory.domain.specification.CIDRSpecification;
import dev.davivieira.topologyinventory.domain.specification.NetworkAmountSpec;
import dev.davivieira.topologyinventory.domain.specification.NetworkAvailabilitySpec;
import dev.davivieira.topologyinventory.domain.vo.IP;
import dev.davivieira.topologyinventory.domain.vo.Id;
import dev.davivieira.topologyinventory.domain.vo.Location;
import dev.davivieira.topologyinventory.domain.vo.Model;
import dev.davivieira.topologyinventory.domain.vo.Network;
import dev.davivieira.topologyinventory.domain.vo.Protocol;
import dev.davivieira.topologyinventory.domain.vo.SwitchType;
import dev.davivieira.topologyinventory.domain.vo.Vendor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.function.Predicate;

@Getter
public class Switch extends Equipment {

    private SwitchType switchType;
    private List<Network> switchNetworks;

    @Setter
    private Id routerId;

    @Builder
    public Switch(Id switchId, Id routerId, Vendor vendor, Model model, IP ip, Location location, SwitchType switchType, List<Network> switchNetworks){
        super(switchId, vendor, model, ip, location);
        this.switchType = switchType;
        this.switchNetworks = switchNetworks;
        this.routerId = routerId;
    }

    public static Predicate<Network> getNetworkProtocolPredicate(Protocol protocol){
        return s -> s.getNetworkAddress().getProtocol().equals(protocol);
    }

    public static Predicate<Switch> getSwitchTypePredicate(SwitchType switchType){
        return s -> s.switchType .equals(switchType);
    }

    public boolean addNetworkToSwitch(Network network) {
        var availabilitySpec = new NetworkAvailabilitySpec(network);
        var cidrSpec = new CIDRSpecification();
        var amountSpec = new NetworkAmountSpec();

        cidrSpec.check(network.getNetworkCidr());
        availabilitySpec.check(this);
        amountSpec.check(this);

        return this.switchNetworks.add(network);
    }

    public boolean removeNetworkFromSwitch(Network network){
        return this.switchNetworks.remove(network);
    }
}
