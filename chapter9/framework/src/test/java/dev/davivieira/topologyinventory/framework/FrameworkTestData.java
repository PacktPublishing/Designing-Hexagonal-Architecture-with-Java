package dev.davivieira.topologyinventory.framework;

import dev.davivieira.topologyinventory.application.ports.output.RouterManagementOutputPort;
import dev.davivieira.topologyinventory.application.ports.output.SwitchManagementOutputPort;
import dev.davivieira.topologyinventory.application.usecases.NetworkManagementUseCase;
import dev.davivieira.topologyinventory.application.usecases.RouterManagementUseCase;
import dev.davivieira.topologyinventory.application.usecases.SwitchManagementUseCase;
import dev.davivieira.topologyinventory.domain.entity.CoreRouter;
import dev.davivieira.topologyinventory.domain.entity.EdgeRouter;
import dev.davivieira.topologyinventory.domain.entity.Router;
import dev.davivieira.topologyinventory.domain.entity.Switch;
import dev.davivieira.topologyinventory.domain.vo.*;
import dev.davivieira.topologyinventory.framework.adapters.input.generic.NetworkManagementGenericAdapter;
import dev.davivieira.topologyinventory.framework.adapters.input.generic.RouterManagementGenericAdapter;
import dev.davivieira.topologyinventory.framework.adapters.input.generic.SwitchManagementGenericAdapter;

import java.util.*;

public class FrameworkTestData {

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

    protected RouterManagementGenericAdapter routerManagementGenericAdapter;
    protected SwitchManagementGenericAdapter switchManagementGenericAdapter;
    protected NetworkManagementGenericAdapter networkManagementGenericAdapter;

    protected void loadPortsAndUseCases() {
        // Load router implementations
        ServiceLoader<RouterManagementUseCase> loaderUseCaseRouter = ServiceLoader.load(RouterManagementUseCase.class);
        RouterManagementUseCase routerManagementUseCase = loaderUseCaseRouter.findFirst().get();
        ServiceLoader<RouterManagementOutputPort> loaderOutputRouter = ServiceLoader.load(RouterManagementOutputPort.class);
        RouterManagementOutputPort routerManagementOutputPort = loaderOutputRouter.findFirst().get();

        // Load switch implementations
        ServiceLoader<SwitchManagementUseCase> loaderUseCaseSwitch = ServiceLoader.load(SwitchManagementUseCase.class);
        SwitchManagementUseCase switchManagementUseCase = loaderUseCaseSwitch.findFirst().get();
        ServiceLoader<SwitchManagementOutputPort> loaderOutputSwitch = ServiceLoader.load(SwitchManagementOutputPort.class);
        SwitchManagementOutputPort switchManagementOutputPort = loaderOutputSwitch.findFirst().get();

        // Load switch implementations
        ServiceLoader<NetworkManagementUseCase> loaderUseCaseNetwork = ServiceLoader.load(NetworkManagementUseCase.class);
        NetworkManagementUseCase networkManagementUseCase = loaderUseCaseNetwork.findFirst().get();

        routerManagementUseCase.setOutputPort(routerManagementOutputPort);
        switchManagementUseCase.setOutputPort(switchManagementOutputPort);
        networkManagementUseCase.setOutputPort(routerManagementOutputPort);

        this.routerManagementGenericAdapter = new RouterManagementGenericAdapter(routerManagementUseCase);
        this.switchManagementGenericAdapter = new SwitchManagementGenericAdapter(routerManagementUseCase, switchManagementUseCase);
        this.networkManagementGenericAdapter = new NetworkManagementGenericAdapter(switchManagementUseCase, networkManagementUseCase);
    }

    public void loadData(){
        this.locationA = Location.builder().
                address("Amos Ln").
                city("Tully").
                state("NY").
                country("United States").
                zipcode(13159).
                latitude(42.797310F).
                longitude(-76.130750F).
                build();
        this.locationB = Location.builder().
                address("Av Republica Argentina 3109").
                city("Curitiba").
                state("PR").
                country("Italy").
                zipcode(80610260).
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
                switchId(Id.withId("f8c3de3d-1fea-4d7c-a8b0-29f63c4c3490")).
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
