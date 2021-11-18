package dev.davivieira.domain.vo;

public class Network {

    private final IP address;
    private final String name;
    private final int cidr;

    public Network(IP address, String name, int cidr){
        if(cidr <1 || cidr>32){
            throw new IllegalArgumentException("Invalid CIDR value");
        }
        this.address = address;
        this.name= name;
        this.cidr = cidr;
    }

    public IP getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public int getCidr() {
        return cidr;
    }

    @Override
    public String toString() {
        return "Network{" +
                "address=" + address +
                ", name='" + name + '\'' +
                ", cidr=" + cidr +
                '}';
    }
}
