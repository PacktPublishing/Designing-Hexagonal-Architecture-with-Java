package dev.davivieira.topologyinventory.framework.adapters.output.h2.data;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.eclipse.persistence.annotations.Convert;
import org.eclipse.persistence.annotations.Converter;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "switches")
@MappedSuperclass
@Converter(name="uuidConverter", converterClass= UUIDTypeConverter.class)
public class SwitchData implements Serializable {

    @Id
    @Column(name="switch_id",
            columnDefinition = "uuid",
            updatable = false )
    @Convert("uuidConverter")
    private UUID switchId;

    @Column(name="router_id")
    @Convert("uuidConverter")
    private UUID routerId;

    @Embedded
    @Enumerated(EnumType.STRING)
    @Column(name="switch_vendor")
    private VendorData switchVendor;

    @Embedded
    @Enumerated(EnumType.STRING)
    @Column(name="switch_model")
    private ModelData switchModel;

    @Enumerated(EnumType.STRING)
    @Embedded
    @Column(name = "switch_type")
    private SwitchTypeData switchType;

    @OneToMany
    @JoinColumn(table = "networks",
            name = "switch_id",
            referencedColumnName = "switch_id")
    private List<NetworkData> networks;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(
                    name = "address",
                    column = @Column(
                            name = "switch_ip_address")),
            @AttributeOverride(
                    name = "protocol",
                    column = @Column(
                            name = "switch_ip_protocol")),
    })
    private IPData ip;

    @ManyToOne
    @JoinColumn(name="location_id")
    private LocationData switchLocation;
}
