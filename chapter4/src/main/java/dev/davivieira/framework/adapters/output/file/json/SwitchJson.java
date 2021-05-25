package dev.davivieira.framework.adapters.output.file.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class SwitchJson {

    @JsonProperty("ip")
    private IPJson ip;

    @JsonProperty("switchType")
    private SwitchTypeJson switchType;

    @JsonProperty("networks")
    private List<NetworkJson> networks;

}
