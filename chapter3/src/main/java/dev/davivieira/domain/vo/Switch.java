package dev.davivieira.domain.vo;

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

    public Switch addNetwork(Network network){
        var networks = new ArrayList<>(Arrays.asList(network));
        networks.add(network);
        return new Switch(this.type, networks, this.address);
    }

    public List<Network> getNetworks() {
        return networks;
    }
}
