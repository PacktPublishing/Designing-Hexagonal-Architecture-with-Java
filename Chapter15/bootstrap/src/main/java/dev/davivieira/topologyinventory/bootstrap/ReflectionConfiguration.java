package dev.davivieira.topologyinventory.bootstrap;

import dev.davivieira.topologyinventory.domain.entity.CoreRouter;
import dev.davivieira.topologyinventory.domain.entity.EdgeRouter;
import dev.davivieira.topologyinventory.domain.entity.Switch;
import dev.davivieira.topologyinventory.domain.vo.IP;
import dev.davivieira.topologyinventory.domain.vo.Id;
import dev.davivieira.topologyinventory.domain.vo.Location;
import dev.davivieira.topologyinventory.domain.vo.Model;
import dev.davivieira.topologyinventory.domain.vo.Network;
import dev.davivieira.topologyinventory.domain.vo.Protocol;
import dev.davivieira.topologyinventory.domain.vo.RouterType;
import dev.davivieira.topologyinventory.domain.vo.SwitchType;
import dev.davivieira.topologyinventory.domain.vo.Vendor;
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
