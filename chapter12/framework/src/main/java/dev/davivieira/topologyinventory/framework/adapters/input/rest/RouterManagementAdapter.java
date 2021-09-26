package dev.davivieira.topologyinventory.framework.adapters.input.rest;

import dev.davivieira.topologyinventory.application.usecases.RouterManagementUseCase;
import dev.davivieira.topologyinventory.domain.entity.CoreRouter;
import dev.davivieira.topologyinventory.domain.entity.Router;
import dev.davivieira.topologyinventory.domain.vo.IP;
import dev.davivieira.topologyinventory.domain.vo.Id;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import dev.davivieira.topologyinventory.framework.adapters.input.rest.request.router.AddRouter;
import dev.davivieira.topologyinventory.framework.adapters.input.rest.request.router.CreateRouter;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@ApplicationScoped
@Path("/router")
public class RouterManagementAdapter {

    @Inject
    RouterManagementUseCase routerManagementUseCase;

    @GET
    @Path("/retrieve/{id}")
    @Operation(operationId = "retrieveRouter", description = "Retrieve a router from the network inventory")
    @Tag(name = "Retrieve router")
    public Uni<Response> retrieveRouter(@PathParam("id") Id id) {
        return Uni.createFrom()
                .item(routerManagementUseCase.retrieveRouter(id))
                .onItem()
                .transform(f -> f != null ? Response.ok(f) : Response.ok(null))
                .onItem()
                .transform(Response.ResponseBuilder::build);
    }

    @DELETE
    @Path("/remove/{id}")
    @Operation(operationId = "removeRouter", description = "Remove a router from the network inventory")
    @Tag(name = "Remove router")
    public Uni<Response> removeRouter(@PathParam("id") Id id) {
        return Uni.createFrom()
                .item(routerManagementUseCase.removeRouter(id))
                .onItem()
                .transform(f -> f != null ? Response.ok(f) : Response.ok(null))
                .onItem()
                .transform(Response.ResponseBuilder::build);
    }

    @POST
    @Path("/create")
    @Operation(operationId = "createRouter", description = "Create and persist a new router on the network inventory")
    @Tag(name = "Create router")
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

    @POST
    @Path("/add")
    @Operation(operationId = "addRouterToCoreRouter", description = "Add a router into a core router")
    @Tag(name = "Add router to a core router")
    public Uni<Response> addRouterToCoreRouter(AddRouter addRouter) {
        Router router = routerManagementUseCase
                .retrieveRouter(Id.withId(addRouter.getRouterId()));
        CoreRouter coreRouter = (CoreRouter) routerManagementUseCase
                .retrieveRouter(Id.withId(addRouter.getCoreRouterId()));

        return Uni.createFrom()
                .item(routerManagementUseCase.
                        addRouterToCoreRouter(router, coreRouter))
                .onItem()
                .transform(f -> f != null ? Response.ok(f) : Response.ok(null))
                .onItem()
                .transform(Response.ResponseBuilder::build);
    }

    @DELETE
    @Path("/remove/{routerId}/from/{coreRouterId}")
    @Operation(operationId = "removeRouterFromCoreRouter", description = "Remove a router from a core router")
    @Tag(name = "Remove router from a core router")
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
