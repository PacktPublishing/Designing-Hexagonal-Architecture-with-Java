package dev.davivieira.topologyinventory.application;

import dev.davivieira.topologyinventory.application.usecases.SwitchManagementUseCase;
import dev.davivieira.topologyinventory.domain.valueobject.IP;
import dev.davivieira.topologyinventory.domain.valueobject.Model;
import dev.davivieira.topologyinventory.domain.valueobject.SwitchType;
import dev.davivieira.topologyinventory.domain.valueobject.Vendor;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SwitchCreate extends ApplicationTestData {

    @Inject
    SwitchManagementUseCase switchManagementUseCase;

    public SwitchCreate(){
        loadData();
    }

    @Given("I provide all required data to create a switch")
    public void i_provide_all_required_data_to_create_a_switch(){
        networkSwitch = this.switchManagementUseCase.createSwitch(
                Vendor.CISCO,
                Model.XYZ0001,
                IP.fromAddress("20.0.0.100"),
                locationA,
                SwitchType.LAYER3
        );
    }

    @Then("A new switch is created")
    public void a_new_switch_is_created() {
        assertNotNull(networkSwitch);
        assertEquals(Vendor.CISCO, networkSwitch.getVendor());
        assertEquals(Model.XYZ0001,networkSwitch.getModel());
        assertEquals(IP.fromAddress("20.0.0.100"), networkSwitch.getIp());
        assertEquals(locationA, networkSwitch.getLocation());
        assertEquals(SwitchType.LAYER3, networkSwitch.getSwitchType());
    }
}
