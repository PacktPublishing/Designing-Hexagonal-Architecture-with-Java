package dev.davivieira.framework.adapters.output.h2.data;

import dev.davivieira.domain.vo.Network;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.eclipse.persistence.annotations.Convert;
import org.eclipse.persistence.annotations.Converter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "networks")
@MappedSuperclass
@Converter(name="uuidConverter", converterClass= UUIDTypeConverter.class)
public class NetworkData implements Serializable {

    @Id
    @Column(name="network_id")
    private int id;

    @Column(name="router_id")
    @Convert("uuidConverter")
    private UUID routerId;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride( name = "address", column = @Column(name = "network_address")),
            @AttributeOverride( name = "protocol", column = @Column(name = "network_protocol")),
    })
    IPData ip;

    @Column(name="network_name")
    String name;

    @Column(name="network_cidr")
    Integer cidr;

    public NetworkData(UUID routerId, IPData ip, String name, Integer cidr) {
        this.routerId = routerId;
        this.ip = ip;
        this.name = name;
        this.cidr = cidr;
    }
}
