package dev.davivieira.topologyinventory.application;

import dev.davivieira.topologyinventory.domain.service.NetworkService;
import dev.davivieira.topologyinventory.domain.vo.Network;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import java.util.function.Predicate;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class NetworkRemove extends ApplicationTestData{

    Predicate<Network> predicate;

    public NetworkRemove(){
        loadData();
    }

    @Given("I know the network I want to remove")
    public void i_know_the_network_i_want_to_remove(){
        predicate = Network.
                getNetworkNamePredicate("TestNetwork");
        network = NetworkService.
                findNetwork(networks, predicate);
        assertEquals("TestNetwork", network.getNetworkName());
    }

    @And("I have a switch to remove a network")
    public void i_have_a_switch_to_remove_a_network(){
        assertNotNull(networkSwitch);
    }

    @Then("I remove the network from the switch")
    public void i_remove_the_network_from_the_switch(){
        this.networkManagementUseCase.
                removeNetworkFromSwitch("Marketing", networkSwitch);
        network = NetworkService.
                findNetwork(networks, predicate);
        assertNull(network);
    }
}
