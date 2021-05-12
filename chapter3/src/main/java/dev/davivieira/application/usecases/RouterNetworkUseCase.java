package dev.davivieira.application.usecases;

import dev.davivieira.domain.entity.Router;
import dev.davivieira.domain.vo.Network;
import dev.davivieira.domain.vo.RouterId;

public interface RouterNetworkUseCase {

    Router addNetworkToRouter(NetworkToRouterCommand networkToRouterCommand);

    class NetworkToRouterCommand {

        private RouterId routerId;

        private Network network; // Name, address and CIDR are network attributes

        public NetworkToRouterCommand(RouterId routerId, Network network){
            this.routerId = routerId;
            this.network = network;
        }

        public RouterId getRouterId() {
            return routerId;
        }
        public Network getNetwork() {
            return network;
        }
    }
}
