package dev.davivieira.application.ports.output;

import dev.davivieira.domain.entity.Router;
import dev.davivieira.domain.vo.RouterId;

public interface RouterNetworkOutputPort {
    Router fetchRouterById(RouterId routerId);

    boolean persistRouter(Router router);
}
