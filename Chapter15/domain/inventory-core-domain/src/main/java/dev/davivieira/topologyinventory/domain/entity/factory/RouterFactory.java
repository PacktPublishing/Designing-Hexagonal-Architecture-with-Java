package dev.davivieira.topologyinventory.domain.entity.factory;

import dev.davivieira.topologyinventory.domain.entity.CoreRouter;
import dev.davivieira.topologyinventory.domain.entity.EdgeRouter;
import dev.davivieira.topologyinventory.domain.entity.Router;
import dev.davivieira.topologyinventory.domain.valueobject.IP;
import dev.davivieira.topologyinventory.domain.valueobject.Id;
import dev.davivieira.topologyinventory.domain.valueobject.Location;
import dev.davivieira.topologyinventory.domain.valueobject.Model;
import dev.davivieira.topologyinventory.domain.valueobject.RouterType;
import dev.davivieira.topologyinventory.domain.valueobject.Vendor;

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
