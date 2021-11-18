package dev.davivieira.topologyinventory.application;

import dev.davivieira.topologyinventory.domain.entity.Switch;
import dev.davivieira.topologyinventory.domain.vo.Id;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import static org.junit.Assert.*;

public class SwitchRemove extends ApplicationTestData{

    Id id;
    Switch switchToBeRemoved;

    public SwitchRemove(){
        loadData();
    }

    @Given("I know the switch I want to remove")
    public void i_know_the_switch_i_want_to_remove(){
        id = Id.withId("f8c3de3d-1fea-4d7c-a8b0-29f63c4c3490");
        switchToBeRemoved = edgeRouter.getSwitches().get(id);
    }
    @And("The switch has no networks")
    public void the_switch_has_no_networks() {
        switchToBeRemoved.removeNetworkFromSwitch(network);
        assertTrue(switchToBeRemoved.getSwitchNetworks().isEmpty());
    }
    @Then("I remove the switch from the edge router")
    public void i_remove_the_switch_from_the_edge_router(){
        assertNotNull(edgeRouter);
        edgeRouter = this.switchManagementUseCase.
                removeSwitchFromEdgeRouter(switchToBeRemoved, edgeRouter);
        assertNull(edgeRouter.getSwitches().get(id));
    }
}
