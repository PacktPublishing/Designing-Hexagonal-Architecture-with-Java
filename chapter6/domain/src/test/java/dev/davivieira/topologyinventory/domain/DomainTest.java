package dev.davivieira.topologyinventory.domain;

import dev.davivieira.topologyinventory.domain.entity.Router;
import dev.davivieira.topologyinventory.domain.entity.Switch;
import dev.davivieira.topologyinventory.domain.service.RouterService;
import dev.davivieira.topologyinventory.domain.service.NetworkService;
import dev.davivieira.topologyinventory.domain.service.SwitchService;
import dev.davivieira.topologyinventory.domain.vo.*;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static junit.framework.TestCase.*;

@FixMethodOrder( MethodSorters.NAME_ASCENDING )
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
        assertTrue(edgeRouter.getSwitches().size()==1);
    }


    @Test
    public void addEdgeToCoreRouter(){
        coreRouter.addRouter(edgeRouter);
        assertTrue(coreRouter.getRouters().size() == 1);
    }

    @Test
    public void addCoreToCoreRouter(){
        coreRouter.addRouter(newCoreRouter);
        assertTrue(coreRouter.getRouters().size() == 2);
    }

    @Test
    public void retrieveRouters(){
        coreRouter.addRouter(edgeRouter);
        assertTrue(coreRouter.getRouters().size() == 1);
    }

    @Test
    public void removeRouter(){
        coreRouter.addRouter(newEdgeRouter);
        var removedRouter = coreRouter.removeRouter(newEdgeRouter);
        assertTrue(removedRouter.getId().equals(Id.withId("ca23800e-9b5a-11eb-a8b3-0242ac130003")));
    }

    @Test
    public void removeSwitch(){
        edgeRouter.addSwitch(networkSwitch);
        networkSwitch.removeNetworkFromSwitch(network);
        var removedSwitch= edgeRouter.removeSwitch(networkSwitch);
        assertTrue(removedSwitch.getId().equals(Id.withId("f8c3de3d-1fea-4d7c-a8b0-29f63c4c3490")));
    }

    @Test
    public void removeNetwork(){
        assertFalse(networkSwitch.getSwitchNetworks().size()==0);
        assertTrue(networkSwitch.removeNetworkFromSwitch(network));
        assertTrue(networkSwitch.getSwitchNetworks().size()==0);
    }

    @Test
    public void filterRouterByType(){
        routers.add(newCoreRouter);
        routers.add(newEdgeRouter);

        var coreRouters = RouterService.filterAndRetrieveRouter(routers,
                Router.getRouterTypePredicate(RouterType.CORE));
        assertTrue(coreRouters.get(0).getRouterType().compareTo(RouterType.CORE)==0);
        assertFalse(coreRouters.get(0).getRouterType().compareTo(RouterType.EDGE)==0);

        var edgeRouters = RouterService.filterAndRetrieveRouter(routers,
                Router.getRouterTypePredicate(RouterType.EDGE));
        assertTrue(edgeRouters.get(0).getRouterType().compareTo(RouterType.EDGE)==0);
        assertFalse(edgeRouters.get(0).getRouterType().compareTo(RouterType.CORE)==0);
    }

    @Test
    public void filterRouterByVendor(){
        routers.add(newCoreRouter);
        routers.add(newEdgeRouter);

        var router = RouterService.filterAndRetrieveRouter(routers,
                Router.getVendorPredicate(Vendor.HP)).get(0);
        assertTrue(router.getVendor().equals(Vendor.HP));
        assertFalse(router.getVendor().equals(Vendor.CISCO));

        router = RouterService.filterAndRetrieveRouter(routers,
                Router.getVendorPredicate(Vendor.CISCO)).get(0);
        assertTrue(router.getVendor().equals(Vendor.CISCO));
        assertFalse(router.getVendor().equals(Vendor.HP));
    }

    @Test
    public void filterRouterByLocation(){
        coreRouter.addRouter(newCoreRouter);
        routers.add(coreRouter);
        var router = RouterService.filterAndRetrieveRouter(routers,
                Router.getCountryPredicate(locationBrazil)).get(0);
        assertTrue(router.getLocation().getCountry().equals(locationBrazil.getCountry()));
    }

    @Test
    public void filterRouterByModel(){
        coreRouter.addRouter(newCoreRouter);
        routers.add(coreRouter);
        var router= RouterService.filterAndRetrieveRouter(routers,
                Router.getModelPredicate(Model.XYZ0001)).get(0);
        assertTrue(router.getModel().equals(Model.XYZ0001));
    }

    @Test
    public void filterSwitchByType(){
        switches.add(networkSwitch);
        var filteredNetworkSwitch = SwitchService.filterAndRetrieveSwitch(switches,
                Switch.getSwitchTypePredicate(SwitchType.LAYER3)).get(0);
        assertTrue(filteredNetworkSwitch.getSwitchType().equals(SwitchType.LAYER3));
    }

    @Test
    public void findRouterById(){
        coreRouter.addRouter(newCoreRouter);
        var id = Id.withId("81579b05-4b4e-4b9b-91f4-75a5a8561296");
        var router = RouterService.findById(routersOfCoreRouter, id);
        assertTrue(router.getId().equals(id));
    }

    @Test
    public void findSwitchById(){
        var id = Id.withId("f8c3de3d-1fea-4d7c-a8b0-29f63c4c3490");
        var retrievedSwitch = SwitchService.findById(switchesOfEdgeRouter, id);
        assertTrue(retrievedSwitch.getId().equals(id));
    }

    @Test
    public void retrieveNetworkByProtocol(){
        var networks = NetworkService.filterAndRetrieveNetworks(this.networks,
                Switch.getNetworkProtocolPredicate(Protocol.IPV4)).get(0);
        assertFalse(networks.getNetworkAddress().getProtocol().equals(Protocol.IPV6));
        assertTrue(networks.getNetworkAddress().getProtocol().equals(Protocol.IPV4));

    }
}
