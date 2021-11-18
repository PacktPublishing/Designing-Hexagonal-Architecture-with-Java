package dev.davivieira.topologyinventory.framework.adapters.input.rest;

import dev.davivieira.topologyinventory.application.usecases.NetworkManagementUseCase;
import dev.davivieira.topologyinventory.application.usecases.SwitchManagementUseCase;
import dev.davivieira.topologyinventory.domain.entity.Switch;
import dev.davivieira.topologyinventory.domain.vo.IP;
import dev.davivieira.topologyinventory.domain.vo.Id;
import dev.davivieira.topologyinventory.domain.vo.Network;
import dev.davivieira.topologyinventory.framework.adapters.input.rest.request.network.AddNetwork;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@ApplicationScoped
@Path("/network")
@Tag(name = "Network Operations", description = "Network management operations")
public class NetworkManagementAdapter {

    @Inject
    SwitchManagementUseCase switchManagementUseCase;
    @Inject
    NetworkManagementUseCase networkManagementUseCase;

    @Transactional
    @POST
    @Path("/add/{switchId}")
    @Operation(operationId = "addNetworkToSwitch", description = "Add network to a switch")
    public Uni<Response> addNetworkToSwitch(AddNetwork addNetwork, @PathParam("switchId") String switchId) {
        Switch networkSwitch = switchManagementUseCase.retrieveSwitch(Id.withId(switchId));

        Network network = Network.builder()
                .networkAddress(IP.fromAddress(addNetwork.getNetworkAddress()))
                .networkName(addNetwork.getNetworkName())
                .networkCidr(addNetwork.getNetworkCidr())
                .build();

        return Uni.createFrom()
                .item(networkManagementUseCase.addNetworkToSwitch(network, networkSwitch))
                .onItem()
                .transform(f -> f != null ? Response.ok(f) : Response.ok(null))
                .onItem()
                .transform(Response.ResponseBuilder::build);
    }

    @Transactional
    @DELETE
    @Path("/{networkName}/from/{switchId}")
    @Operation(operationId = "removeNetworkFromSwitch", description = "Remove network from a switch")
    public Uni<Response> removeNetworkFromSwitch(@PathParam("networkName") String networkName, @PathParam("switchId") String switchId) {
        Switch networkSwitch = switchManagementUseCase.retrieveSwitch(Id.withId(switchId));

        return Uni.createFrom()
                .item(networkManagementUseCase.removeNetworkFromSwitch(networkName, networkSwitch))
                .onItem()
                .transform(f -> f != null ? Response.ok(f) : Response.ok(null))
                .onItem()
                .transform(Response.ResponseBuilder::build);
    }
}
