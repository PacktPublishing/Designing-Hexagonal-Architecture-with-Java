package dev.davivieira.topologyinventory.framework.adapters.input.rest.request.network;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class AddNetwork {

    @JsonProperty
    private String networkAddress;

    @JsonProperty
    private String networkName;

    @JsonProperty
    private int networkCidr;
}
