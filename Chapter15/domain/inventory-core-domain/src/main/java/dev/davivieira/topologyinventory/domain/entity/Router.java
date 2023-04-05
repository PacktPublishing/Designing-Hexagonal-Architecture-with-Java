package dev.davivieira.topologyinventory.domain.entity;

import dev.davivieira.topologyinventory.domain.valueobject.IP;
import dev.davivieira.topologyinventory.domain.valueobject.Id;
import dev.davivieira.topologyinventory.domain.valueobject.Location;
import dev.davivieira.topologyinventory.domain.valueobject.Model;
import dev.davivieira.topologyinventory.domain.valueobject.RouterType;
import dev.davivieira.topologyinventory.domain.valueobject.Vendor;
import lombok.Getter;
import lombok.Setter;

import java.util.function.Predicate;

@Getter
public abstract class Router extends Equipment {

    protected final RouterType routerType;

    @Setter
    public Id parentRouterId;

    public static Predicate<Equipment> getRouterTypePredicate(RouterType routerType){
        return r -> ((Router)r).getRouterType().equals(routerType);
    }

    public static Predicate<Equipment> getModelPredicate(Model model){
        return r -> r.getModel().equals(model);
    }

    public static Predicate<Equipment> getCountryPredicate(Location location){
        return p -> p.location.getCountry().equals(location.getCountry());
    }

    protected Router(Id id, Id parentRouterId, Vendor vendor, Model model, IP ip, Location location, RouterType routerType) {
        super(id, vendor, model, ip, location);
        this.routerType = routerType;
        this.parentRouterId = parentRouterId;
    }
}
