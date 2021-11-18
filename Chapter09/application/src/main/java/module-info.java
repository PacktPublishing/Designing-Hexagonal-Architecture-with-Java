module application {
    requires domain;
    requires static lombok;

    exports dev.davivieira.topologyinventory.application.ports.input;
    exports dev.davivieira.topologyinventory.application.ports.output;
    exports dev.davivieira.topologyinventory.application.usecases;

    provides dev.davivieira.topologyinventory.application.usecases.RouterManagementUseCase
            with dev.davivieira.topologyinventory.application.ports.input.RouterManagementInputPort;
    provides dev.davivieira.topologyinventory.application.usecases.SwitchManagementUseCase
            with dev.davivieira.topologyinventory.application.ports.input.SwitchManagementInputPort;
    provides dev.davivieira.topologyinventory.application.usecases.NetworkManagementUseCase
            with dev.davivieira.topologyinventory.application.ports.input.NetworkManagementInputPort;
}