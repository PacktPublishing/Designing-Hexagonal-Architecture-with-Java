package dev.davivieira.topologyinventory.domain.entity;

import dev.davivieira.topologyinventory.domain.vo.*;
import lombok.Getter;

import java.util.function.Predicate;

@Getter
public abstract class Router extends Equipment {

    protected final RouterType routerType;

    public static Predicate<Router> getRouterTypePredicate(RouterType routerType){
        return r -> r.getRouterType().equals(routerType);
    }

    public static Predicate<Router> getVendorPredicate(Vendor vendor){
        return r -> r.getVendor().equals(vendor);
    }

    public static Predicate<Router> getModelPredicate(Model model){
        return r -> r.getModel().equals(model);
    }

    public static Predicate<Router> getCountryPredicate(Location location){
        return p -> p.location.getCountry().equals(location.getCountry());
    }

    public Router(Id id, Vendor vendor, Model model, IP ip, Location location, RouterType routerType) {
        super(id, vendor, model, ip, location);
        this.routerType = routerType;
    }
}