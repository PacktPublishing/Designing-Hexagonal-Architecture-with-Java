package dev.davivieira.framework.adapters.output.h2.data;

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
@Table(name = "routers")
@SecondaryTable(name = "switches")
@MappedSuperclass
@Converter(name="uuidConverter", converterClass= UUIDTypeConverter.class)
public class RouterData implements Serializable {

    @Id
    @Column(name="router_id", columnDefinition = "uuid", updatable = false )
    @Convert("uuidConverter")
    private UUID routerId;

    @Embedded
    @Enumerated(EnumType.STRING)
    @Column(name="router_type")
    private RouterTypeData routerType;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(table = "switches", name = "router_id", referencedColumnName = "router_id")
    private SwitchData networkSwitch;
}
