package dev.davivieira.topologyinventory.framework.adapters.input.rest;

import dev.davivieira.topologyinventory.domain.valueobject.Network;
import dev.davivieira.topologyinventory.framework.adapters.input.rest.request.network.AddNetwork;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.IOException;

import static dev.davivieira.topologyinventory.framework.adapters.input.rest.deserializer.SwitchDeserializer.getSwitchDeserialized;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.wildfly.common.Assert.assertFalse;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class NetworkManagementAdapterTest {

    @Test
    @Order(1)
    public void addNetworkToSwitch() throws IOException {
        var networkName = "TestNetwork";
        var switchId = "922dbcd5-d071-41bd-920b-00f83eb4bb46";
        var addNetwork = AddNetwork.builder()
                .networkAddress("60.0.0.1")
                .networkName(networkName)
                .networkCidr(24)
                .build();
        var switchStr = given()
                .contentType("application/json")
                .pathParam("switchId", switchId)
                .body(addNetwork)
                .when()
                .post("/network/add/{switchId}")
                .then()
                .statusCode(200)
                .extract()
                .asString();
        var networks = getSwitchDeserialized(switchStr).getSwitchNetworks();
        var networkExists = networks
                .stream()
                .map(Network::getNetworkName)
                .anyMatch(name -> name.equals(networkName));

        assertTrue(networkExists);
    }

    @Test
    @Order(2)
    public void removeNetworkFromSwitch() throws IOException {
        var networkName = "Engineering";
        var switchId = "922dbcd5-d071-41bd-920b-00f83eb4bb46";

        var switchStr = given()
                .contentType("application/json")
                .pathParam("networkName", networkName)
                .pathParam("switchId", switchId)
                .when()
                .delete("/network/{networkName}/from/{switchId}")
                .then()
                .statusCode(200)
                .extract()
                .asString();

        var networks = getSwitchDeserialized(switchStr).getSwitchNetworks();
        var networkExists = networks
                .stream()
                .map(Network::getNetworkName)
                .anyMatch(name -> name.equals(networkName));

        assertFalse(networkExists);
    }
}
