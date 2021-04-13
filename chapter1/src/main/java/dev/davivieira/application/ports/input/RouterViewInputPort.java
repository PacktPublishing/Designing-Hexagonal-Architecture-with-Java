package dev.davivieira.application.ports.input;

import dev.davivieira.application.ports.output.RouterViewOutputPort;
import dev.davivieira.application.usecases.RouterViewUseCase;
import dev.davivieira.domain.Router;
import dev.davivieira.domain.Type;

import java.util.List;

public class RouterViewInputPort implements RouterViewUseCase {

    private RouterViewOutputPort routerListOutputPort;

    private Router router;

    public RouterViewInputPort(RouterViewOutputPort routerGraphOutputPort) {
        this.routerListOutputPort = routerGraphOutputPort;
    }

    @Override
    public List<Router> getRelatedRouters(RelatedRoutersCommand relatedRoutersCommand) {
        var type = relatedRoutersCommand.getType();
        var routers = routerListOutputPort.fetchRelatedRouters();
        return fetchRelatedEdgeRouters(type, routers);
    }

    private List<Router> fetchRelatedEdgeRouters(Type type, List<Router> routers){
        return router.checkRouter(type, routers);
    }
}
