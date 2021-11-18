module application {
    exports dev.davivieira.topologyinventory.application.usecases;
    exports dev.davivieira.topologyinventory.application.ports.output;
    exports dev.davivieira.topologyinventory.application.ports.input;
    requires domain;
    requires static lombok;
}
