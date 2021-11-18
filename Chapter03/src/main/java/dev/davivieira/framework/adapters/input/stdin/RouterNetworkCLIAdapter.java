package dev.davivieira.framework.adapters.input.stdin;

import dev.davivieira.application.ports.input.RouterNetworkInputPort;
import dev.davivieira.application.usecases.RouterNetworkUseCase;
import dev.davivieira.domain.entity.Router;
import dev.davivieira.domain.vo.IP;
import dev.davivieira.domain.vo.Network;
import dev.davivieira.domain.vo.RouterId;
import dev.davivieira.framework.adapters.output.file.RouterNetworkFileAdapter;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class RouterNetworkCLIAdapter {

    RouterNetworkUseCase routerNetworkUseCase;

    public RouterNetworkCLIAdapter(){
        setAdapters();
    }

    public Router addNetwork(RouterId routerId, Network network){
        return routerNetworkUseCase.addNetworkToRouter(routerId, network);
    }

    private void setAdapters(){
        this.routerNetworkUseCase = new RouterNetworkInputPort(RouterNetworkFileAdapter.getInstance());
    }

}
