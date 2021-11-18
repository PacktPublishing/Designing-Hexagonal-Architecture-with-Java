package dev.davivieira.application.ports.input;

import dev.davivieira.application.ports.output.RouterViewOutputPort;
import dev.davivieira.application.usecases.RouterViewUseCase;
import dev.davivieira.domain.Router;

import static dev.davivieira.domain.Router.retrieveRouter;

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
        return Router.retrieveRouter(routers, filter);
    }
}
