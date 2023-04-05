package dev.davivieira.topologyinventory.framework.adapters.input.rest.request.aswitch;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import dev.davivieira.topologyinventory.domain.valueobject.Location;
import dev.davivieira.topologyinventory.domain.valueobject.Model;
import dev.davivieira.topologyinventory.domain.valueobject.SwitchType;
import dev.davivieira.topologyinventory.domain.valueobject.Vendor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class CreateSwitch {

    @JsonProperty
    private Vendor vendor;

    @JsonProperty
    private Model model;

    @JsonProperty
    private String ip;

    @JsonProperty
    private Location location;

    @JsonProperty
    private SwitchType switchType;
}
