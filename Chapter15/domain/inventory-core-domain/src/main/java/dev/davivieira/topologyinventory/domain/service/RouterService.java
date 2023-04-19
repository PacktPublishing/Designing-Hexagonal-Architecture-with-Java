package dev.davivieira.topologyinventory.domain.service;

import dev.davivieira.topologyinventory.domain.entity.Equipment;
import dev.davivieira.topologyinventory.domain.entity.Router;
import dev.davivieira.topologyinventory.domain.valueobject.Id;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public final class RouterService {

    private RouterService() {
    }

    public static List<Router> filterAndRetrieveRouter(List<Router> routers, Predicate<Equipment> routerPredicate){
        return routers
                .stream()
                .filter(routerPredicate)
                .toList();
    }

    public static Router findById(Map<Id,Router> routers, Id id){
        return routers.get(id);
    }
}
