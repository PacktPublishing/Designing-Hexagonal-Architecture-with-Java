package dev.davivieira.application.ports.input;

import dev.davivieira.application.ports.output.RouterNetworkOutputPort;
import dev.davivieira.application.usecases.RouterNetworkUseCase;
import dev.davivieira.domain.entity.Router;
import dev.davivieira.domain.service.NetworkOperation;
import dev.davivieira.domain.vo.Network;
import dev.davivieira.domain.vo.RouterId;

public class RouterNetworkInputPort implements RouterNetworkUseCase {

    RouterNetworkOutputPort routerNetworkOutputPort;

    public RouterNetworkInputPort(){

    }

        public RouterNetworkInputPort(RouterNetworkOutputPort routerNetworkOutputPort){
            this.routerNetworkOutputPort = routerNetworkOutputPort;
        }

    @Override
    public Router addNetworkToRouter(NetworkToRouterCommand networkToRouterCommand) {
        var router = fetchRouter(networkToRouterCommand.getRouterId());
        var network = networkToRouterCommand.getNetwork();
        return createNetwork(router, network);
    }

    private Router fetchRouter(RouterId routerId) {
        return routerNetworkOutputPort.fetchRouterById(routerId);
    }

    private Router createNetwork(Router router, Network network) {
        var routerWithNewNetwork = NetworkOperation.createNewNetwork(router, network);
        return persistNetwork(routerWithNewNetwork) ?
                routerWithNewNetwork:
                router;
    }

    private boolean persistNetwork(Router router) {
        return routerNetworkOutputPort.persistRouter(router);
    }
}
