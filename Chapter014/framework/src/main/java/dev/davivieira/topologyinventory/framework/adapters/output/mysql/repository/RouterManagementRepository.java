package dev.davivieira.topologyinventory.framework.adapters.output.mysql.repository;

import dev.davivieira.topologyinventory.framework.adapters.output.mysql.data.RouterData;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import java.util.UUID;

@ApplicationScoped
public class RouterManagementRepository implements PanacheRepositoryBase<RouterData, UUID> {

}
