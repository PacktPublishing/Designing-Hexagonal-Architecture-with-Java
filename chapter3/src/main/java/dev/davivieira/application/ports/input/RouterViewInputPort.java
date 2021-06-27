package dev.davivieira.application.ports.input;

import dev.davivieira.application.ports.output.RouterViewOutputPort;
import dev.davivieira.application.usecases.RouterViewUseCase;
import dev.davivieira.domain.entity.Router;
import dev.davivieira.domain.service.RouterSearch;

import java.util.List;
import java.util.function.Predicate;

public class RouterViewInputPort implements RouterViewUseCase {

    private RouterViewOutputPort routerListOutputPort;

    public RouterViewInputPort(RouterViewOutputPort routerViewOutputPort) {
        this.routerListOutputPort = routerViewOutputPort;
    }

    @Override
    public List<Router> getRouters(Predicate<Router> filter) {
        var routers = routerListOutputPort.fetchRouters();
        return RouterSearch.retrieveRouter(routers, filter);
    }
}
