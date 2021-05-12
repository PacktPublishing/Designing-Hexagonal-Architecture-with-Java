package dev.davivieira.domain.entity;

import dev.davivieira.domain.vo.*;

import java.util.List;
import java.util.UUID;

public class Router {

    private final RouterType type;
    private final RouterId id;
    private List<IP> address;
    private Switch networkSwitch;

    public Router(RouterType type, RouterId id) {
        this.type = type;
        this.id = id;
    }

    public boolean isType(RouterType type){
        return this.type == type;
    }

    public void addNetworkToSwitch(Network network){
        this.networkSwitch = networkSwitch.addNetwork(network);
    }

    public Network createNetwork(IP address, String name, int cidr){
        return new Network(address, name, cidr);
    }

    public List<Network> retrieveNetworks(){
        return networkSwitch.getNetworks();
    }

    @Override
    public String toString() {
        return "Router{" +
                "type=" + type +
                ", id=" + id +
                '}';
    }
}