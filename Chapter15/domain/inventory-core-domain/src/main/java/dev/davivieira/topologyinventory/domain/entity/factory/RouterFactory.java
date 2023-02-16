package dev.davivieira.topologyinventory.domain.entity.factory;

import dev.davivieira.topologyinventory.domain.entity.CoreRouter;
import dev.davivieira.topologyinventory.domain.entity.EdgeRouter;
import dev.davivieira.topologyinventory.domain.entity.Router;
import dev.davivieira.topologyinventory.domain.vo.IP;
import dev.davivieira.topologyinventory.domain.vo.Id;
import dev.davivieira.topologyinventory.domain.vo.Location;
import dev.davivieira.topologyinventory.domain.vo.Model;
import dev.davivieira.topologyinventory.domain.vo.RouterType;
import dev.davivieira.topologyinventory.domain.vo.Vendor;

public final class RouterFactory {

    private RouterFactory() {
        // not instantiated
    }
    public static Router getRouter(Id id,
                                   Vendor vendor,
                                   Model model,
                                   IP ip,
                                   Location location,
                                   RouterType routerType){

        return switch (routerType) {
            case CORE -> CoreRouter.builder().
                    id(id == null ? Id.withoutId() : id).
                    vendor(vendor).
                    model(model).
                    ip(ip).
                    location(location).
                    routerType(routerType).
                    build();
            case EDGE -> EdgeRouter.builder().
                    id(id == null ? Id.withoutId() : id).
                    vendor(vendor).
                    model(model).
                    ip(ip).
                    location(location).
                    routerType(routerType).
                    build();
        };
    }
}
