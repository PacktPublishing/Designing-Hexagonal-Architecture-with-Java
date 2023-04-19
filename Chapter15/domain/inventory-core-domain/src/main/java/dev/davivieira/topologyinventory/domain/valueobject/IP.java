package dev.davivieira.topologyinventory.domain.valueobject;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;

@Getter
@ToString
@EqualsAndHashCode
public class IP {

    private final String ipAddress;
    private final Protocol protocol;

    public IP(String ipAddress) {
      this.ipAddress = Objects.requireNonNull(ipAddress, "Null IP address");
      if (ipAddress.length() <= 15) {
          this.protocol = Protocol.IPV4;
      } else {
        this.protocol = Protocol.IPV6;
      }
    }

    public static IP fromAddress(String ipAddress){
        return new IP(ipAddress);
    }
}
