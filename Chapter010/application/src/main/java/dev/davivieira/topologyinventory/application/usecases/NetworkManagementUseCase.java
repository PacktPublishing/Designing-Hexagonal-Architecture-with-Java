package dev.davivieira.topologyinventory.application.usecases;

import dev.davivieira.topologyinventory.application.ports.output.RouterManagementOutputPort;
import dev.davivieira.topologyinventory.domain.entity.Switch;
import dev.davivieira.topologyinventory.domain.vo.IP;
import dev.davivieira.topologyinventory.domain.vo.Network;

public interface NetworkManagementUseCase {

    void setOutputPort(RouterManagementOutputPort routerNetworkOutputPort);

    Network createNetwork(
            IP networkAddress,
            String networkName,
            int networkCidr);

    Switch addNetworkToSwitch(Network network, Switch networkSwitch);

    Switch removeNetworkFromSwitch(String name, Switch networkSwitch);
}
