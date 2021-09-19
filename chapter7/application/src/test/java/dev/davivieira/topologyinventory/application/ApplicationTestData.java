package dev.davivieira.topologyinventory.application;

import dev.davivieira.topologyinventory.application.ports.input.NetworkManagementInputPort;
import dev.davivieira.topologyinventory.application.ports.input.RouterManagementInputPort;
import dev.davivieira.topologyinventory.application.ports.input.SwitchManagementInputPort;
import dev.davivieira.topologyinventory.application.usecases.NetworkManagementUseCase;
import dev.davivieira.topologyinventory.application.usecases.RouterManagementUseCase;
import dev.davivieira.topologyinventory.application.usecases.SwitchManagementUseCase;
import dev.davivieira.topologyinventory.domain.entity.CoreRouter;
import dev.davivieira.topologyinventory.domain.entity.EdgeRouter;
import dev.davivieira.topologyinventory.domain.entity.Router;
import dev.davivieira.topologyinventory.domain.entity.Switch;
import dev.davivieira.topologyinventory.domain.vo.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApplicationTestData {

    protected RouterManagementUseCase routerManagementUseCase;

    protected SwitchManagementUseCase switchManagementUseCase;

    protected NetworkManagementUseCase networkManagementUseCase;

    protected Router router;

    protected List<Router> routers = new ArrayList<>();

    protected List<Switch> switches = new ArrayList<>();

    protected List<Network> networks = new ArrayList<>();

    protected Map<Id, Router> routersOfCoreRouter = new HashMap<>();

    protected Map<Id, Switch> switchesOfEdgeRouter = new HashMap<>();

    protected Network network;

    protected Switch networkSwitch;

    protected CoreRouter coreRouter;

    protected CoreRouter newCoreRouter;

    protected EdgeRouter edgeRouter;

    protected EdgeRouter newEdgeRouter;

    protected Location locationA;

    protected Location locationB;

    public void loadData(){
        this.routerManagementUseCase = new RouterManagementInputPort();
        this.switchManagementUseCase = new SwitchManagementInputPort();
        this.networkManagementUseCase = new NetworkManagementInputPort();
        this.locationA = Location.builder().
                address("Av Republica Argentina 3109").
                city("Curitiba").
                state("PR").
                country("Brazil").
                zipCode(80610260).
                latitude(10F).
                longitude(-10F).
                build();
        this.locationB = Location.builder().
                address("Av Republica Argentina 3109").
                city("Curitiba").
                state("PR").
                country("Brazil").
                zipCode(80610260).
                latitude(10F).
                longitude(-10F).
                build();
        this.network  = Network.builder().
                networkAddress(IP.fromAddress("20.0.0.0")).
                networkName("TestNetwork").
                networkCidr(8).
                build();
        this.networks.add(network);
        this.networkSwitch = Switch.builder().
                id(Id.withId("f8c3de3d-1fea-4d7c-a8b0-29f63c4c3490")).
                vendor(Vendor.CISCO).
                model(Model.XYZ0004).
                ip(IP.fromAddress("20.0.0.100")).
                location(locationA).
                switchType(SwitchType.LAYER3).
                switchNetworks(networks).
                build();
        this.switchesOfEdgeRouter.put(networkSwitch.getId(), networkSwitch);
        this.edgeRouter = EdgeRouter.builder().
                id(Id.withoutId()).
                vendor(Vendor.CISCO).
                model(Model.XYZ0002).
                ip(IP.fromAddress("20.0.0.1")).
                location(locationA).
                routerType(RouterType.EDGE).
                switches(switchesOfEdgeRouter).
                build();
        this.routersOfCoreRouter.put(edgeRouter.getId(), edgeRouter);
        this.coreRouter = CoreRouter.builder().
                id(Id.withoutId()).
                vendor(Vendor.HP).
                model(Model.XYZ0001).
                ip(IP.fromAddress("10.0.0.1")).
                location(locationA).
                routerType(RouterType.CORE).
                routers(routersOfCoreRouter).
                build();
        this.newCoreRouter = CoreRouter.builder().
                id(Id.withId("81579b05-4b4e-4b9b-91f4-75a5a8561296")).
                vendor(Vendor.HP).
                model(Model.XYZ0001).
                ip(IP.fromAddress("10.1.0.1")).
                location(locationA).
                routerType(RouterType.CORE).
                build();
        this.coreRouter.addRouter(newCoreRouter);
        this.newEdgeRouter = EdgeRouter.builder().
                id(Id.withId("ca23800e-9b5a-11eb-a8b3-0242ac130003")).
                vendor(Vendor.CISCO).
                model(Model.XYZ0002).
                ip(IP.fromAddress("20.1.0.1")).
                location(locationA).
                routerType(RouterType.EDGE).
                build();
    }
}
