package dev.davivieira.framework.adapters.output.file.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class SwitchJson {

    @JsonProperty("switchId")
    private UUID switchId;

    @JsonProperty("ip")
    private IPJson ip;

    @JsonProperty("switchType")
    private SwitchTypeJson switchType;

    @JsonProperty("networks")
    private List<NetworkJson> networks;

}
