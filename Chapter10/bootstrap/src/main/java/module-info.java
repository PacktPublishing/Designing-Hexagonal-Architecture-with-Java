module dev.davivieira.bootstrap {
    requires quarkus.core;
    requires domain;
    requires application;
    //requires framework;
    requires java.ws.rs;
    requires jakarta.enterprise.cdi.api;
    requires jakarta.inject.api;
    requires java.validation;
    requires quarkus.hibernate.orm;
    requires quarkus.jdbc.h2;
    requires java.persistence;
    requires java.transaction;
    requires lombok;
}