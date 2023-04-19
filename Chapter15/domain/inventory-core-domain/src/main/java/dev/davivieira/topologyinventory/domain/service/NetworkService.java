package dev.davivieira.topologyinventory.domain.service;

import dev.davivieira.topologyinventory.domain.valueobject.Network;

import java.util.List;
import java.util.function.Predicate;

public final class NetworkService {

    private NetworkService() {}

    public static List<Network> filterAndRetrieveNetworks(List<Network> networks, Predicate<Network> networkPredicate){
        return networks
                .stream()
                .filter(networkPredicate)
                .toList();
    }

    public static Network findNetwork(List<Network> networks, Predicate<Network> networkPredicate){
        return networks
                .stream()
                .filter(networkPredicate)
                .findFirst()
                .orElse(null);
    }
}
