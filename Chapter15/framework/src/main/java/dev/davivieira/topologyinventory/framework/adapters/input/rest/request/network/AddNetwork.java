package dev.davivieira.topologyinventory.framework.adapters.input.rest.request.network;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class AddNetwork {

    @JsonProperty
    @NotNull
    private String networkAddress;

    @JsonProperty
    private String networkName;

    @JsonProperty
    @Min(1)
    @Max(35)
    private int networkCidr;
}
