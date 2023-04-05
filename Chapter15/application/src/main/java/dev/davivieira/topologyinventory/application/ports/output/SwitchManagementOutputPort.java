package dev.davivieira.topologyinventory.application.ports.output;

import dev.davivieira.topologyinventory.domain.entity.Switch;
import dev.davivieira.topologyinventory.domain.valueobject.Id;

public interface SwitchManagementOutputPort {
    Switch retrieveSwitch(Id id);
}
