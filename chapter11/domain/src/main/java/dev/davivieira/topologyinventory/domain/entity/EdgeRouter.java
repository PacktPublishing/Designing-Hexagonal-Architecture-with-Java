package dev.davivieira.topologyinventory.domain.entity;

import dev.davivieira.topologyinventory.domain.specification.EmptyNetworkSpec;
import dev.davivieira.topologyinventory.domain.specification.SameCountrySpec;
import dev.davivieira.topologyinventory.domain.specification.SameIpSpec;
import dev.davivieira.topologyinventory.domain.vo.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Getter
@ToString
public class EdgeRouter extends Router {

    @Setter
    private Map<Id, Switch> switches;

    @Builder
    public EdgeRouter(Id id, Vendor vendor, Model model, IP ip, Location location, RouterType routerType, Map<Id, Switch> switches) {
        super(id, vendor, model, ip, location, routerType);
        this.switches = switches;
    }

    public void addSwitch(Switch anySwitch){
        var sameCountryRouterSpec = new SameCountrySpec(this);
        var sameIpSpec = new SameIpSpec(this);

        if(!sameCountryRouterSpec.isSatisfiedBy(anySwitch))
            throw new UnsupportedOperationException("The Switch should be in the same country as the Edge Router");

        if(!sameIpSpec.isSatisfiedBy(anySwitch)){
            throw new UnsupportedOperationException("It's not possible to add a switch with same router's IP");
        }
        this.switches.put(anySwitch.id,anySwitch);
    }

    public Switch removeSwitch(Switch anySwitch){
        var emptyNetworkSpec = new EmptyNetworkSpec();
        if(!emptyNetworkSpec.isSatisfiedBy(anySwitch)){
            throw new UnsupportedOperationException("It's not possible to remove a switch with networks attached to it");
        }
        return this.switches.remove(anySwitch.id);
    }
}