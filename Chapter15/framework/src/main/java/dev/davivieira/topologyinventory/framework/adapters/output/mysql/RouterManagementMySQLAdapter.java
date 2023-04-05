package dev.davivieira.topologyinventory.framework.adapters.output.mysql;

import dev.davivieira.topologyinventory.application.ports.output.RouterManagementOutputPort;
import dev.davivieira.topologyinventory.domain.entity.Router;
import dev.davivieira.topologyinventory.domain.valueobject.Id;
import dev.davivieira.topologyinventory.framework.adapters.output.mysql.mappers.RouterH2Mapper;
import dev.davivieira.topologyinventory.framework.adapters.output.mysql.repository.RouterManagementRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import static io.quarkus.hibernate.reactive.panache.Panache.withTransaction;

@ApplicationScoped
public class RouterManagementMySQLAdapter implements RouterManagementOutputPort {

    @Inject
    RouterManagementRepository routerManagementRepository;

    @Override
    public Router retrieveRouter(Id id) {
        var routerData = routerManagementRepository
                .findById(id.getUuid())
                .subscribe()
                .asCompletionStage()
                .join();
        return RouterH2Mapper.routerDataToDomain(routerData);
    }

    @Override
    public Router removeRouter(Id id) {
        var removed = routerManagementRepository
                .deleteById(id.getUuid())
                .subscribe()
                .asCompletionStage()
                .join();

        if (Boolean.FALSE.equals(removed)) {
            throw new InternalError();
        }

        return null;
    }

    @Override
    public Router persistRouter(Router router) {
        var routerData = RouterH2Mapper.routerDomainToData(router);
        withTransaction(()->routerManagementRepository.persist(routerData));
        return router;
    }
}
