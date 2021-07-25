package dev.davivieira.topologyinventory.framework.adapters.input.generic;

import dev.davivieira.topologyinventory.application.ports.input.NetworkManagementInputPort;
import dev.davivieira.topologyinventory.application.ports.input.RouterManagementInputPort;
import dev.davivieira.topologyinventory.application.ports.input.SwitchManagementInputPort;
import dev.davivieira.topologyinventory.application.usecases.NetworkManagementUseCase;
import dev.davivieira.topologyinventory.application.usecases.RouterManagementUseCase;
import dev.davivieira.topologyinventory.application.usecases.SwitchManagementUseCase;
import dev.davivieira.topologyinventory.domain.entity.CoreRouter;
import dev.davivieira.topologyinventory.domain.entity.Router;
import dev.davivieira.topologyinventory.domain.vo.*;
import dev.davivieira.topologyinventory.framework.adapters.output.h2.RouterManagementH2Adapter;

public class RouterManagementGenericAdapter {

    public RouterManagementUseCase routerManagementUseCase;
    public SwitchManagementUseCase switchManagementUseCase;
    public NetworkManagementUseCase networkManagementUseCase;

    public RouterManagementGenericAdapter(){
        setPorts();
    }

    private void setPorts(){
        this.routerManagementUseCase = new RouterManagementInputPort(
                RouterManagementH2Adapter.getInstance()
        );
        this.switchManagementUseCase = new SwitchManagementInputPort();
        this.networkManagementUseCase = new NetworkManagementInputPort();
    }

    public Router retrieveRouter(Id id){
        return routerManagementUseCase.retrieveRouter(id);
    }

    public Router createRouter(Vendor vendor,
                                   Model model,
                                   IP ip,
                                   Location location,
                                   RouterType routerType){
        return routerManagementUseCase.createRouter(
                null,
                vendor,
                model,
                ip,
                location,
                routerType
        );
    }

    public Router addRouterToCoreRouter(Router router, CoreRouter coreRouter){
        return this.routerManagementUseCase.
                addRouterToCoreRouter(router, coreRouter);
    }
}
