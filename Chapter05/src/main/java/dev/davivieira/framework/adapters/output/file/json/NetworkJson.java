package dev.davivieira.framework.adapters.output.file.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class NetworkJson {

    @JsonProperty("ip")
    IPJson ip;
    @JsonProperty("networkName")
    String networkName;
    @JsonProperty("networkCidr")
    String cidr;
}
