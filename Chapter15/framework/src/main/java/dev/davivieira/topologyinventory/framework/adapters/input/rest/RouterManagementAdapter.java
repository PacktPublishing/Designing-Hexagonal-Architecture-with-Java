package dev.davivieira.topologyinventory.framework.adapters.input.rest;

import dev.davivieira.topologyinventory.application.usecases.RouterManagementUseCase;
import dev.davivieira.topologyinventory.domain.entity.CoreRouter;
import dev.davivieira.topologyinventory.domain.entity.Router;
import dev.davivieira.topologyinventory.domain.valueobject.IP;
import dev.davivieira.topologyinventory.domain.valueobject.Id;
import dev.davivieira.topologyinventory.framework.adapters.input.rest.request.router.CreateRouter;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Consumes("application/json")
@Produces("application/json")
@ApplicationScoped
@Path("/router")
@Tag(name = "Router Operations", description = "Router management operations")
public class RouterManagementAdapter {

    @Inject
    RouterManagementUseCase routerManagementUseCase;

    @Transactional
    @GET
    @Path("/{id}")
    @Operation(operationId = "retrieveRouter", description = "Retrieve a router from the network inventory")
    public Uni<Response> retrieveRouter(@PathParam("id") Id id) {
        return Uni.createFrom()
                .item(routerManagementUseCase.retrieveRouter(id))
                .onItem()
                .transform(f -> f != null ? Response.ok(f) : Response.ok(null))
                .onItem()
                .transform(Response.ResponseBuilder::build);
    }

    @Transactional
    @DELETE
    @Path("/{id}")
    @Operation(operationId = "removeRouter", description = "Remove a router from the network inventory")
    public Uni<Response> removeRouter(@PathParam("id") Id id) {
        return Uni.createFrom()
                .item(routerManagementUseCase.removeRouter(id))
                .onItem()
                .transform(f -> f != null ? Response.ok(f) : Response.ok(null))
                .onItem()
                .transform(Response.ResponseBuilder::build);
    }

    @Transactional
    @POST
    @Path("/")
    @Operation(operationId = "createRouter", description = "Create and persist a new router on the network inventory")
    public Uni<Response> createRouter(CreateRouter createRouter) {
        var router = routerManagementUseCase.createRouter(
                null,
                createRouter.getVendor(),
                createRouter.getModel(),
                IP.fromAddress(createRouter.getIp()),
                createRouter.getLocation(),
                createRouter.getRouterType()

        );

        return Uni.createFrom()
                .item(routerManagementUseCase.persistRouter(router))
                .onItem()
                .transform(f -> f != null ? Response.ok(f) : Response.ok(null))
                .onItem()
                .transform(Response.ResponseBuilder::build);
    }

    @Transactional
    @POST
    @Path("/{routerId}/to/{coreRouterId}")
    @Operation(operationId = "addRouterToCoreRouter", description = "Add a router into a core router")
    public Uni<Response> addRouterToCoreRouter(
            @PathParam("routerId") String routerId, @PathParam("coreRouterId") String coreRouterId) {
        Router router = routerManagementUseCase
                .retrieveRouter(Id.withId(routerId));
        CoreRouter coreRouter = (CoreRouter) routerManagementUseCase
                .retrieveRouter(Id.withId(coreRouterId));

        return Uni.createFrom()
                .item(routerManagementUseCase.
                        addRouterToCoreRouter(router, coreRouter))
                .onItem()
                .transform(f -> f != null ? Response.ok(f) : Response.ok(null))
                .onItem()
                .transform(Response.ResponseBuilder::build);
    }

    @Transactional
    @DELETE
    @Path("/{routerId}/from/{coreRouterId}")
    @Operation(operationId = "removeRouterFromCoreRouter", description = "Remove a router from a core router")
    public Uni<Response> removeRouterFromCoreRouter(
            @PathParam("routerId") String routerId, @PathParam("coreRouterId") String coreRouterId) {
        Router router = routerManagementUseCase
                .retrieveRouter(Id.withId(routerId));
        CoreRouter coreRouter = (CoreRouter) routerManagementUseCase
                .retrieveRouter(Id.withId(coreRouterId));

        return Uni.createFrom()
                .item(routerManagementUseCase.
                        removeRouterFromCoreRouter(router, coreRouter))
                .onItem()
                .transform(f -> f != null ? Response.ok(f) : Response.ok(null))
                .onItem()
                .transform(Response.ResponseBuilder::build);
    }
}
