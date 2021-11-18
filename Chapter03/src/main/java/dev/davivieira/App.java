package dev.davivieira;

import dev.davivieira.domain.vo.IP;
import dev.davivieira.domain.vo.Network;
import dev.davivieira.domain.vo.RouterId;
import dev.davivieira.framework.adapters.input.stdin.RouterNetworkCLIAdapter;

public class App {

    public static void main(String... args) {
        var cli = new RouterNetworkCLIAdapter();
        var routerId = RouterId.withId("ca23800e-9b5a-11eb-a8b3-0242ac130003");
        var network = new Network(new IP("20.0.0.0"), "Marketing", 8);
        var router = cli.addNetwork(routerId, network);
        System.out.println(router);
    }
}