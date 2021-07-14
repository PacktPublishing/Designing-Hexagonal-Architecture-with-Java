package dev.davivieira.framework.adapters.input;

import dev.davivieira.application.usecases.RouterNetworkUseCase;
import dev.davivieira.domain.entity.Router;
import dev.davivieira.domain.vo.IP;
import dev.davivieira.domain.vo.Network;
import dev.davivieira.domain.vo.RouterId;
import java.util.Map;

public abstract class RouterNetworkAdapter {

    protected Router router;
    protected RouterNetworkUseCase routerNetworkUseCase;

    public RouterNetworkAdapter(){
        setPorts();
    }

    public Router addNetworkToRouter(Map<String, String> params){
        var routerId = RouterId.withId(params.get("routerId"));
        var network = new Network(IP.fromAddress(params.get("address")),
                params.get("name"),
                Integer.valueOf(params.get("cidr")));
        return routerNetworkUseCase.addNetworkToRouter(routerId, network);
    }

    protected abstract void setPorts();

    public abstract Router processRequest(Object requestParams);
}
