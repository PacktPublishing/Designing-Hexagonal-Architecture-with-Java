package dev.davivieira.framework.adapters.output.file.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class RouterJson {

    @JsonProperty("routerId")
    private UUID routerId;

    @JsonProperty("routerType")
    private RouterTypeJson routerType;

    @JsonProperty("switch")
    private SwitchJson networkSwitch;
}
