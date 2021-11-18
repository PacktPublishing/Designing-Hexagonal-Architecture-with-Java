package dev.davivieira.framework.adapters.output.file.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class IPJson {

    @JsonProperty("address")
    private String address;

    @JsonProperty("protocol")
    private ProtocolJson protocol;

    private IPJson(String address){
        if(address == null)
            throw new IllegalArgumentException("Null IP address");
        this.address = address;
        if(address.length()<=15) {
            this.protocol = ProtocolJson.IPV4;
        } else {
            this.protocol = ProtocolJson.IPV6;
        }
    }

    public static IPJson fromAddress(String ipAddress) {
        return new IPJson(ipAddress);
    }
}
