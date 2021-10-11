import dev.davivieira.topologyinventory.framework.adapters.output.mysql.RouterManagementMySQLAdapter;
import dev.davivieira.topologyinventory.framework.adapters.output.mysql.SwitchManagementMySQLAdapter;

module framework {
    requires domain;
    requires application;
    requires static lombok;
    requires org.hibernate.orm.core;
    requires java.sql;
    requires java.persistence;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;
    requires jakarta.enterprise.cdi.api;
    requires jakarta.inject.api;
    requires java.ws.rs;
    requires io.smallrye.mutiny;
    requires java.xml.bind;
    requires smallrye.common.annotation;
    requires com.fasterxml.jackson.annotation;
    requires microprofile.openapi.api;
    requires quarkus.hibernate.reactive.panache;
    requires java.transaction;
    requires io.vertx.core;
    requires microprofile.context.propagation.api;

    exports dev.davivieira.topologyinventory.framework.adapters.output.mysql.data;
    opens dev.davivieira.topologyinventory.framework.adapters.output.mysql.data;

    provides dev.davivieira.topologyinventory.application.ports.output.RouterManagementOutputPort
            with RouterManagementMySQLAdapter;
    provides dev.davivieira.topologyinventory.application.ports.output.SwitchManagementOutputPort
            with SwitchManagementMySQLAdapter;

    uses dev.davivieira.topologyinventory.application.usecases.RouterManagementUseCase;
    uses dev.davivieira.topologyinventory.application.usecases.SwitchManagementUseCase;
    uses dev.davivieira.topologyinventory.application.usecases.NetworkManagementUseCase;
    uses dev.davivieira.topologyinventory.application.ports.output.RouterManagementOutputPort;
    uses dev.davivieira.topologyinventory.application.ports.output.SwitchManagementOutputPort;
}