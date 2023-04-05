package dev.davivieira.topologyinventory.bootstrap;

import dev.davivieira.topologyinventory.domain.entity.CoreRouter;
import dev.davivieira.topologyinventory.domain.entity.EdgeRouter;
import dev.davivieira.topologyinventory.domain.entity.Switch;
import dev.davivieira.topologyinventory.domain.valueobject.IP;
import dev.davivieira.topologyinventory.domain.valueobject.Id;
import dev.davivieira.topologyinventory.domain.valueobject.Location;
import dev.davivieira.topologyinventory.domain.valueobject.Model;
import dev.davivieira.topologyinventory.domain.valueobject.Network;
import dev.davivieira.topologyinventory.domain.valueobject.Protocol;
import dev.davivieira.topologyinventory.domain.valueobject.RouterType;
import dev.davivieira.topologyinventory.domain.valueobject.SwitchType;
import dev.davivieira.topologyinventory.domain.valueobject.Vendor;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection(targets = {
        CoreRouter.class,
        EdgeRouter.class,
        Switch.class,
        Id.class,
        IP.class,
        Location.class,
        Model.class,
        Network.class,
        Protocol.class,
        RouterType.class,
        SwitchType.class,
        Vendor.class,
})
public class ReflectionConfiguration {
    // Used for registering reflections
}
