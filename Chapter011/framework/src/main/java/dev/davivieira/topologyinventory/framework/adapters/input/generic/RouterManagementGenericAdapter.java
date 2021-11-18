package dev.davivieira.topologyinventory.framework.adapters.input.generic;

import dev.davivieira.topologyinventory.application.usecases.RouterManagementUseCase;
import dev.davivieira.topologyinventory.domain.entity.CoreRouter;
import dev.davivieira.topologyinventory.domain.entity.Router;
import dev.davivieira.topologyinventory.domain.vo.IP;
import dev.davivieira.topologyinventory.domain.vo.Id;
import dev.davivieira.topologyinventory.domain.vo.Location;
import dev.davivieira.topologyinventory.domain.vo.Model;
import dev.davivieira.topologyinventory.domain.vo.RouterType;
import dev.davivieira.topologyinventory.domain.vo.Vendor;

import javax.enterprise.context.ApplicationScoped;

import javax.inject.Inject;

@ApplicationScoped
public class RouterManagementGenericAdapter {

    @Inject
    private RouterManagementUseCase routerManagementUseCase;

    /**
     * GET /router/retrieve/{id}
     * */
    public Router retrieveRouter(Id id) {
        return routerManagementUseCase.retrieveRouter(id);
    }

    /**
     * GET /router/remove/{id}
     * */
    public Router removeRouter(Id id) {
        return routerManagementUseCase.removeRouter(id);
    }

    /**
     * POST /router/create
     * */
    public Router createRouter(Vendor vendor,
                               Model model,
                               IP ip,
                               Location location,
                               RouterType routerType){
        var router = routerManagementUseCase.createRouter(
                null,
                vendor,
                model,
                ip,
                location,
                routerType

        );
        return routerManagementUseCase.persistRouter(router);
    }

    /**
     * POST /router/add
     * */
    public Router addRouterToCoreRouter(Id routerId, Id coreRouterId) {
        Router router = routerManagementUseCase.retrieveRouter(routerId);
        CoreRouter coreRouter = (CoreRouter) routerManagementUseCase.retrieveRouter(coreRouterId);
        return routerManagementUseCase.
                addRouterToCoreRouter(router, coreRouter);
    }

    /**
     * POST /router/remove
     * */
    public Router removeRouterFromCoreRouter(Id routerId, Id coreRouterId) {
        Router router = routerManagementUseCase.retrieveRouter(routerId);
        CoreRouter coreRouter = (CoreRouter) routerManagementUseCase.retrieveRouter(coreRouterId);
        return routerManagementUseCase.
                removeRouterFromCoreRouter(router, coreRouter);
    }
}
