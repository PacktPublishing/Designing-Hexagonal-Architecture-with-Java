package dev.davivieira.topologyinventory.application.ports.output;

import dev.davivieira.topologyinventory.domain.entity.Router;
import dev.davivieira.topologyinventory.domain.valueobject.Id;

public interface RouterManagementOutputPort {
    Router retrieveRouter(Id id);

    Router removeRouter(Id id);

    Router persistRouter(Router router);
}
