package dev.davivieira.topologyinventory.domain.vo;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.function.Predicate;

@Builder
@Getter
@ToString
@EqualsAndHashCode
public class Network {

    private IP networkAddress;
    private String networkName;
    private int networkCidr;

    public static Predicate<Network> getNetworkProtocolPredicate(Protocol protocol){
        return s -> s.getNetworkAddress().getProtocol().equals(protocol);
    }

    public static Predicate<Network> getNetworkNamePredicate(String name){
        return s -> s.getNetworkName().equals(name);
    }

    public Network(IP networkAddress, String networkName, int networkCidr){
        if(networkCidr <1 || networkCidr>32){
            throw new IllegalArgumentException("Invalid CIDR value");
        }
        this.networkAddress = networkAddress;
        this.networkName = networkName;
        this.networkCidr = networkCidr;
    }
}
