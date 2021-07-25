package dev.davivieira.topologyinventory.domain.entity;

import dev.davivieira.topologyinventory.domain.specification.*;
import dev.davivieira.topologyinventory.domain.vo.*;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@Getter
@ToString
public class CoreRouter extends Router{

    @Getter
    private Map<Id, Router> routers;

    @Builder
    public CoreRouter(Id id, Vendor vendor, Model model, IP ip, Location location, RouterType routerType,  Map<Id, Router> routers) {
        super(id, vendor, model, ip, location, routerType);
        this.routers = routers == null ? new HashMap<Id, Router>() : routers;
    }

    public CoreRouter addRouter(Router anyRouter){
        var sameCountryRouterSpec = new SameCountrySpec(this);
        var sameIpSpec = new SameIpSpec(this);

        if(!sameCountryRouterSpec.isSatisfiedBy(anyRouter))
            throw new UnsupportedOperationException("The Edge Router should be in the same country as the Core Router");

        if(!sameIpSpec.isSatisfiedBy(anyRouter)){
            throw new UnsupportedOperationException("It's not possible to attach routers with the same IP");
        }
        this.routers.put(anyRouter.id, anyRouter);
        return this;
    }

    public Router removeRouter(Router anyRouter){
        var emptyRoutersSpec = new EmptyRouterSpec();
        var emptySwitchSpec = new EmptySwitchSpec();

        switch (anyRouter.routerType) {
            case CORE:
                var coreRouter = (CoreRouter)anyRouter;
                if (!emptyRoutersSpec.isSatisfiedBy(coreRouter)){
                    throw new UnsupportedOperationException("It isn't allowed to remove a core router with other routers attached to it");
                }
                break;
            case EDGE:
                var edgeRouter = (EdgeRouter)anyRouter;
                if (!emptySwitchSpec.isSatisfiedBy(edgeRouter)) {
                    throw new UnsupportedOperationException("It isn't allowed to remove an edge router with a switch attached to it");
                }
        }
        return this.routers.remove(anyRouter.id);
    }
}
