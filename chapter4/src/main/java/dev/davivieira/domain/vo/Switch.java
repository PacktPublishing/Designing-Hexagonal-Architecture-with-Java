package dev.davivieira.domain.vo;

import dev.davivieira.domain.entity.Router;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Switch {

    private SwitchType type;
    private List<Network> networks;
    private IP address;

    public Switch (SwitchType type, List<Network> networks, IP address){
        this.type = type;
        this.networks = networks;
        this.address = address;
    }

    public Switch addNetwork(Network network, Router router){
        List<Network> newNetworks = new ArrayList<>();

        router.retrieveNetworks().forEach(net ->{
            newNetworks.add(net);
        });

        newNetworks.add(network);
        return new Switch(this.type, newNetworks, this.address);
    }

    public List<Network> getNetworks() {
        return networks;
    }

    public SwitchType getType() {
        return type;
    }

    public IP getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "Switch{" +
                "type=" + type +
                ", networks=" + networks +
                ", address=" + address +
                '}';
    }
}
