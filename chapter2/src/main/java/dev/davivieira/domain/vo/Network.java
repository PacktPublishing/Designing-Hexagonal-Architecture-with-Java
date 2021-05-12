package dev.davivieira.domain.vo;

public class Network {

    private IP address;
    private String name;
    private int cidr;

    public Network(IP address, String name, int cidr){
        if(cidr <1 || cidr>32){
            throw new IllegalArgumentException("Invalid CIDR value");
        }
        this.address = address;
        this.cidr = cidr;
    }

    public IP getAddress() {
        return address;
    }

    public int getCidr() {
        return cidr;
    }

}
