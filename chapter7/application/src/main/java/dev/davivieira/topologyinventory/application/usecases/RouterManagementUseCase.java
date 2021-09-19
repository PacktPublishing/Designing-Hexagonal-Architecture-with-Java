package dev.davivieira.topologyinventory.application.usecases;

import dev.davivieira.topologyinventory.domain.entity.CoreRouter;
import dev.davivieira.topologyinventory.domain.entity.Router;
import dev.davivieira.topologyinventory.domain.vo.IP;
import dev.davivieira.topologyinventory.domain.vo.Id;
import dev.davivieira.topologyinventory.domain.vo.Location;
import dev.davivieira.topologyinventory.domain.vo.Model;
import dev.davivieira.topologyinventory.domain.vo.RouterType;
import dev.davivieira.topologyinventory.domain.vo.Vendor;


public interface RouterManagementUseCase {

    Router createRouter(
            Vendor vendor,
            Model model,
            IP ip,
            Location location,
            RouterType routerType);

    CoreRouter addRouterToCoreRouter(
            Router router, CoreRouter coreRouter);

    Router removeRouterFromCoreRouter(
            Router router, CoreRouter coreRouter);

    Router retrieveRouter(Id id);

    Router persistRouter(Router router);
}
