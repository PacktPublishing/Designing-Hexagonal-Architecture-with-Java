package dev.davivieira.topologyinventory.domain;

import dev.davivieira.topologyinventory.domain.entity.Router;
import dev.davivieira.topologyinventory.domain.entity.Switch;
import dev.davivieira.topologyinventory.domain.service.RouterService;
import dev.davivieira.topologyinventory.domain.service.NetworkService;
import dev.davivieira.topologyinventory.domain.service.SwitchService;
import dev.davivieira.topologyinventory.domain.vo.*;
import org.junit.Test;


import static junit.framework.TestCase.*;


public class DomainTest extends DomainTestData {

    public DomainTest(){
        loadData();
    }

    @Test
    public void addNetworkToSwitch(){
        var newNetwork  = Network.builder().
                networkAddress(IP.fromAddress("30.0.0.0")).
                networkName("NewNetwork").
                networkCidr(8).
                build();
        assertTrue(networkSwitch.addNetworkToSwitch(newNetwork));
    }

    @Test
    public void addSwitchToEdgeRouter(){
        edgeRouter.addSwitch(networkSwitch);
        assertEquals(1,edgeRouter.getSwitches().size());
    }

    @Test
    public void addEdgeToCoreRouter(){
        coreRouter.addRouter(edgeRouter);
        assertEquals(1,coreRouter.getRouters().size());
    }

    @Test
    public void addCoreToCoreRouter(){
        coreRouter.addRouter(newCoreRouter);
        assertEquals(2,coreRouter.getRouters().size());
    }

    @Test
    public void removeRouter(){
        coreRouter.addRouter(newEdgeRouter);
        var expectedId = Id.withId("ca23800e-9b5a-11eb-a8b3-0242ac130003");
        var actualId = coreRouter.removeRouter(newEdgeRouter).getId();
        assertEquals(expectedId, actualId);
    }

    @Test
    public void removeSwitch(){
        edgeRouter.addSwitch(networkSwitch);
        networkSwitch.removeNetworkFromSwitch(network);
        var expectedId = Id.withId("f8c3de3d-1fea-4d7c-a8b0-29f63c4c3490");
        var actualId= edgeRouter.removeSwitch(networkSwitch).getId();
        assertEquals(expectedId, actualId);
    }

    @Test
    public void removeNetwork(){
        assertEquals(1, networkSwitch.getSwitchNetworks().size());
        assertTrue(networkSwitch.removeNetworkFromSwitch(network));
        assertEquals(0, networkSwitch.getSwitchNetworks().size());
    }

    @Test
    public void filterRouterByType(){
        routers.add(newCoreRouter);
        routers.add(newEdgeRouter);

        var coreRouters = RouterService.filterAndRetrieveRouter(routers,
                Router.getRouterTypePredicate(RouterType.CORE));
        var actualCoreType = coreRouters.get(0).getRouterType();
        assertEquals(RouterType.CORE, actualCoreType);

        var edgeRouters = RouterService.filterAndRetrieveRouter(routers,
                Router.getRouterTypePredicate(RouterType.EDGE));
        var actualEdgeType = edgeRouters.get(0).getRouterType();
        assertEquals(RouterType.EDGE, actualEdgeType);
    }

    @Test
    public void filterRouterByVendor(){
        routers.add(newCoreRouter);
        routers.add(newEdgeRouter);

        var actualVendor = RouterService.filterAndRetrieveRouter(routers,
                Router.getVendorPredicate(Vendor.HP)).get(0).getVendor();
        assertEquals(actualVendor, Vendor.HP);

        actualVendor = RouterService.filterAndRetrieveRouter(routers,
                Router.getVendorPredicate(Vendor.CISCO)).get(0).getVendor();
        assertEquals(actualVendor, Vendor.CISCO);
    }

    @Test
    public void filterRouterByLocation(){
        coreRouter.addRouter(newCoreRouter);
        routers.add(coreRouter);
        var actualCountry = RouterService.filterAndRetrieveRouter(routers,
                Router.getCountryPredicate(locationA)).get(0).getLocation().getCountry();
        assertEquals(actualCountry, locationA.getCountry());
    }

    @Test
    public void filterRouterByModel(){
        coreRouter.addRouter(newCoreRouter);
        routers.add(coreRouter);
        var actualModel= RouterService.filterAndRetrieveRouter(routers,
                Router.getModelPredicate(Model.XYZ0001)).get(0).getModel();
        assertEquals(actualModel, Model.XYZ0001);
    }

    @Test
    public void filterSwitchByType(){
        switches.add(networkSwitch);
        var actualSwitchType = SwitchService.filterAndRetrieveSwitch(switches,
                Switch.getSwitchTypePredicate(SwitchType.LAYER3)).get(0).getSwitchType();
        assertEquals(actualSwitchType, SwitchType.LAYER3);
    }

    @Test
    public void filterNetworkByProtocol(){
        var expectedProtocol = Protocol.IPV4;
        var actualProtocol = NetworkService.filterAndRetrieveNetworks(this.networks,
                Switch.getNetworkProtocolPredicate(Protocol.IPV4)).get(0).getNetworkAddress().getProtocol();
        assertEquals(expectedProtocol, actualProtocol);
    }

    @Test
    public void findRouterById(){
        coreRouter.addRouter(newCoreRouter);
        var expectedId = Id.withId("81579b05-4b4e-4b9b-91f4-75a5a8561296");
        var actualId = RouterService.findById(routersOfCoreRouter, expectedId).getId();
        assertEquals(expectedId, actualId);
    }

    @Test
    public void findSwitchById(){
        var expectedId = Id.withId("f8c3de3d-1fea-4d7c-a8b0-29f63c4c3490");
        var actualId = SwitchService.findById(switchesOfEdgeRouter, expectedId).getId();
        assertEquals(expectedId, actualId);
    }
}
