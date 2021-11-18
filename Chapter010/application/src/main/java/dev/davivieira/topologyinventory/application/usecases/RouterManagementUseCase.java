package dev.davivieira.topologyinventory.application.usecases;

import dev.davivieira.topologyinventory.application.ports.output.RouterManagementOutputPort;
import dev.davivieira.topologyinventory.domain.entity.CoreRouter;
import dev.davivieira.topologyinventory.domain.entity.Router;
import dev.davivieira.topologyinventory.domain.vo.*;

public interface RouterManagementUseCase {

    void setOutputPort(RouterManagementOutputPort routerManagementOutputPort);

    Router createRouter(
            Id id,
            Vendor vendor,
            Model model,
            IP ip,
            Location location,
            RouterType routerType);

    Router removeRouter(Id id);

    Router retrieveRouter(Id id);

    Router persistRouter(Router router);

    Router addRouterToCoreRouter(
            Router router, CoreRouter coreRouter);

    Router removeRouterFromCoreRouter(
            Router router, CoreRouter coreRouter);
}
