package dev.davivieira.topologyinventory.domain.entity;

import dev.davivieira.topologyinventory.domain.specification.EmptyRouterSpec;
import dev.davivieira.topologyinventory.domain.specification.EmptySwitchSpec;
import dev.davivieira.topologyinventory.domain.specification.SameCountrySpec;
import dev.davivieira.topologyinventory.domain.specification.SameIpSpec;
import dev.davivieira.topologyinventory.domain.valueobject.IP;
import dev.davivieira.topologyinventory.domain.valueobject.Id;
import dev.davivieira.topologyinventory.domain.valueobject.Location;
import dev.davivieira.topologyinventory.domain.valueobject.Model;
import dev.davivieira.topologyinventory.domain.valueobject.RouterType;
import dev.davivieira.topologyinventory.domain.valueobject.Vendor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@Getter
@ToString
public class CoreRouter extends Router {

    @Setter
    private Map<Id, Router> routers;

    @Builder
    public CoreRouter(Id id,
                      Id parentRouterId,
                      Vendor vendor,
                      Model model,
                      IP ip,
                      Location location,
                      RouterType routerType,
                      Map<Id, Router> routers) {
        super(id, parentRouterId, vendor, model, ip, location, routerType);
        this.routers = routers == null ? new HashMap<>() : routers;
    }

    public CoreRouter addRouter(Router anyRouter) {
        var sameCountryRouterSpec = new SameCountrySpec(this);
        var sameIpSpec = new SameIpSpec(this);

        sameCountryRouterSpec.check(anyRouter);
        sameIpSpec.check(anyRouter);

        this.routers.put(anyRouter.id, anyRouter);

        return this;
    }

    public Router removeRouter(Router anyRouter) {
        var emptyRoutersSpec = new EmptyRouterSpec();
        var emptySwitchSpec = new EmptySwitchSpec();

        switch (anyRouter.routerType) {
            case CORE -> {
                var coreRouter = (CoreRouter) anyRouter;
                emptyRoutersSpec.check(coreRouter);
            }
            case EDGE -> {
                var edgeRouter = (EdgeRouter) anyRouter;
                emptySwitchSpec.check(edgeRouter);
            }
        }
        return this.routers.remove(anyRouter.id);
    }
}
