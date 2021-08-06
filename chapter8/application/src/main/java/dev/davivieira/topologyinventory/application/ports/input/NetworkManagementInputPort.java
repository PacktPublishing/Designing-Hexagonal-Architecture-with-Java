package dev.davivieira.topologyinventory.application.ports.input;

import dev.davivieira.topologyinventory.application.ports.output.RouterManagementOutputPort;
import dev.davivieira.topologyinventory.application.usecases.NetworkManagementUseCase;
import dev.davivieira.topologyinventory.domain.entity.EdgeRouter;
import dev.davivieira.topologyinventory.domain.entity.Switch;
import dev.davivieira.topologyinventory.domain.vo.IP;
import dev.davivieira.topologyinventory.domain.vo.Id;
import dev.davivieira.topologyinventory.domain.vo.Network;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NetworkManagementInputPort implements NetworkManagementUseCase {

    RouterManagementOutputPort routerManagementOutputPort;

    public NetworkManagementInputPort(RouterManagementOutputPort routerNetworkOutputPort){
        this.routerManagementOutputPort = routerNetworkOutputPort;
    }

    @Override
    public Network createNetwork(
            IP networkAddress, String networkName, int networkCidr) {
        return Network
                .builder()
                .networkAddress(networkAddress)
                .networkName(networkName)
                .networkCidr(networkCidr)
                .build();
    }

    @Override
    public Switch addNetworkToSwitch(Network network, Switch networkSwitch) {
        Id routerId = networkSwitch.getRouterId();
        Id switchId = networkSwitch.getId();
        EdgeRouter edgeRouter = (EdgeRouter) routerManagementOutputPort
                .retrieveRouter(routerId);
        Switch switchToAddNetwork = edgeRouter
                .getSwitches()
                .get(switchId);
        switchToAddNetwork.addNetworkToSwitch(network);
        routerManagementOutputPort.persistRouter(edgeRouter);
        return switchToAddNetwork;
    }

    @Override
    public Switch removeNetworkFromSwitch(Network network, Switch networkSwitch) {
        Id routerId = networkSwitch.getRouterId();
        Id switchId = networkSwitch.getId();
        EdgeRouter edgeRouter = (EdgeRouter) routerManagementOutputPort
                .retrieveRouter(routerId);
        Switch switchToRemoveNetwork = edgeRouter
                .getSwitches()
                .get(switchId);
        switchToRemoveNetwork.removeNetworkFromSwitch(network);
        routerManagementOutputPort.persistRouter(edgeRouter);
        return switchToRemoveNetwork.removeNetworkFromSwitch(network)
                ? switchToRemoveNetwork
                : null;
    }
}
