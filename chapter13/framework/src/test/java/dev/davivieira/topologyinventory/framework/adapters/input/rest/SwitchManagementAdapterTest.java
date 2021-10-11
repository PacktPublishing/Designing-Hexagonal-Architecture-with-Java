package dev.davivieira.topologyinventory.framework.adapters.input.rest;

import dev.davivieira.topologyinventory.domain.entity.EdgeRouter;
import dev.davivieira.topologyinventory.domain.vo.Id;
import dev.davivieira.topologyinventory.domain.vo.Model;
import dev.davivieira.topologyinventory.domain.vo.SwitchType;
import dev.davivieira.topologyinventory.domain.vo.Vendor;
import dev.davivieira.topologyinventory.framework.adapters.input.rest.request.aswitch.CreateSwitch;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.IOException;

import static dev.davivieira.topologyinventory.framework.adapters.input.rest.RouterManagementAdapterTest.createLocation;
import static dev.davivieira.topologyinventory.framework.adapters.input.rest.deserializer.RouterDeserializer.getRouterDeserialized;
import static dev.davivieira.topologyinventory.framework.adapters.input.rest.deserializer.SwitchDeserializer.getSwitchDeserialized;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SwitchManagementAdapterTest {

    @Test
    @Order(1)
    public void retrieveSwitch() throws IOException {
        var expectedSwitchId = "922dbcd5-d071-41bd-920b-00f83eb4bb46";
        var switchStr = given()
                .contentType("application/json")
                .pathParam("switchId", expectedSwitchId)
                .when()
                .get("/switch/retrieve/{switchId}")
                .then()
                .statusCode(200)
                .extract()
                .asString();
        var actualSwitchId = getSwitchDeserialized(switchStr).getId().getUuid().toString();

        assertEquals(expectedSwitchId, actualSwitchId);
    }

    @Test
    @Order(2)
    public void createAndAddSwitchToEdgeRouter() throws IOException {
        var expectedSwitchIP = "15.0.0.2";
        var edgeRouterId = "b07f5187-2d82-4975-a14b-bdbad9a8ad46";
        var createSwitch = CreateSwitch.builder()
                .vendor(Vendor.CISCO)
                .model(Model.XYZ0001)
                .ip(expectedSwitchIP)
                .location(createLocation("United States"))
                .switchType(SwitchType.LAYER3)
                .build();
        var routerStr = given()
                .contentType("application/json")
                .pathParam("edgeRouterId", edgeRouterId)
                .body(createSwitch)
                .when()
                .post("/switch/create/{edgeRouterId}")
                .then()
                .statusCode(200)
                .extract()
                .asString();
        var switches = ((EdgeRouter)getRouterDeserialized(routerStr)).getSwitches();
        var containsSwitch = switches.values()
                .stream()
                .map(aSwitch -> aSwitch.getIp().getIpAddress())
                .anyMatch(ip->ip.equals(expectedSwitchIP));

        assertTrue(containsSwitch);
    }

    @Test
    @Order(3)
    public void removeSwitchFromEdgeRouter() throws IOException {
        var expectedSwitchId = "922dbcd5-d071-41bd-920b-00f83eb4bb47";
        var edgeRouterId = "b07f5187-2d82-4975-a14b-bdbad9a8ad46";

        var edgeRouterStr = given()
                .contentType("application/json")
                .pathParam("expectedSwitchId", expectedSwitchId)
                .pathParam("edgeRouterId", edgeRouterId)
                .when()
                .delete("/switch/remove/{expectedSwitchId}/from/{edgeRouterId}")
                .then()
                .statusCode(200)
                .extract()
                .asString();
        var switches = ((EdgeRouter)getRouterDeserialized(edgeRouterStr)).getSwitches();

        assertFalse(switches.containsKey(Id.withId(expectedSwitchId)));
    }
}
