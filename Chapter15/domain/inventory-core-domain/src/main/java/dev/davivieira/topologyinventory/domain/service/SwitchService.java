package dev.davivieira.topologyinventory.domain.service;

import dev.davivieira.topologyinventory.domain.entity.Switch;
import dev.davivieira.topologyinventory.domain.vo.Id;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public final class SwitchService {

    private SwitchService() {
        // should not be instantiated
    }
    public static List<Switch> filterAndRetrieveSwitch(List<Switch> switches, Predicate<Switch> switchPredicate){
        return switches
                .stream()
                .filter(switchPredicate)
                .toList();
    }

    public static Switch findById(Map<Id,Switch> switches, Id id) {
        return switches.get(id);
    }
}
