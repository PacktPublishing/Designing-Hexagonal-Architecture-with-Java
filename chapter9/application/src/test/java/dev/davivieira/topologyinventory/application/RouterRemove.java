package dev.davivieira.topologyinventory.application;

import dev.davivieira.topologyinventory.domain.entity.CoreRouter;
import dev.davivieira.topologyinventory.domain.entity.EdgeRouter;
import dev.davivieira.topologyinventory.domain.entity.Router;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import static dev.davivieira.topologyinventory.domain.vo.RouterType.CORE;
import static dev.davivieira.topologyinventory.domain.vo.RouterType.EDGE;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class RouterRemove extends ApplicationTestData {

    CoreRouter coreRouterToBeRemoved;

    public RouterRemove(){
        loadData();
    }
    //Removing an edge router from a core router
    @Given("The core router has at least one edge router connected to it")
    public void the_core_router_has_at_least_one_edge_router_connected_to_it(){
        var predicate = Router.getRouterTypePredicate(EDGE);
        edgeRouter = (EdgeRouter) this.coreRouter.
                getRouters().
                entrySet().
                stream().
                map(routerMap -> routerMap.getValue()).
                filter(predicate).
                findFirst().
                get();
        assertEquals(EDGE, edgeRouter.getRouterType());
    }
    @And("The switch has no networks attached to it")
    public void the_switch_has_no_networks_attached_to_it(){
        var networksSize = networkSwitch.getSwitchNetworks().size();
        assertEquals(1, networksSize);
        networkSwitch.removeNetworkFromSwitch(network);
        networksSize = networkSwitch.getSwitchNetworks().size();
        assertEquals(0, networksSize);
    }
    @And("The edge router has no switches attached to it")
    public void the_edge_router_has_no_switches_attached_to_it(){
        var switchesSize = edgeRouter.getSwitches().size();
        assertEquals(1, switchesSize);
        edgeRouter.removeSwitch(networkSwitch);
        switchesSize = edgeRouter.getSwitches().size();
        assertEquals(0, switchesSize);
    }
    @Then("I remove the edge router from the core router")
    public void edge_router_is_removed_from_core_router(){
        var actualID = edgeRouter.getId();
        var expectedID = this.routerManagementUseCase.
                removeRouterFromCoreRouter(edgeRouter, coreRouter).
                getId();
        assertEquals(expectedID, actualID);
    }
    //Removing a core router from another core router
    @Given("The core router has at least one core router connected to it")
    public void the_core_router_has_at_least_one_core_router_connected_to_it(){
        var predicate = Router.getRouterTypePredicate(CORE);
        coreRouterToBeRemoved = (CoreRouter) this.coreRouter.
                getRouters().
                entrySet().
                stream().
                map(routerMap -> routerMap.getValue()).
                filter(predicate).
                findFirst().
                get();
        assertEquals(CORE, coreRouterToBeRemoved.getRouterType());
    }
    @And("The core router has no other routers connected to it")
    public void the_core_router_no_other_routers_connected_to_it(){
        assertTrue(coreRouterToBeRemoved.getRouters().isEmpty());
    }
    @Then("I remove the core router from another core router")
    public void i_remove_the_core_router_from_another_core_router(){
        var actualId = coreRouterToBeRemoved.getId();
        var expectedId =  this.coreRouter.removeRouter(coreRouterToBeRemoved).getId();
        assertEquals(expectedId, actualId);
    }
}
