package dev.davivieira.topologyinventory.framework;

import dev.davivieira.topologyinventory.domain.entity.CoreRouter;
import dev.davivieira.topologyinventory.domain.vo.*;
import dev.davivieira.topologyinventory.framework.adapters.input.generic.RouterManagementGenericAdapter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RouterTest extends FrameworkTestData {

    RouterManagementGenericAdapter routerManagementGenericAdapter;


    public RouterTest() {
        this.routerManagementGenericAdapter = new RouterManagementGenericAdapter();
        loadData();
    }

    @Test
    public void retrieveRouter() {
        var id = Id.withId("b832ef4f-f894-4194-8feb-a99c2cd4be0c");
        var actualId = routerManagementGenericAdapter.
                retrieveRouter(id).getId().getUuid();
        assertEquals(id.getUuid(), actualId);
    }

    @Test
    public void createRouter() {
        var routerId  = this.
                routerManagementGenericAdapter.createRouter(
                Vendor.DLINK,
                Model.XYZ0001,
                IP.fromAddress("40.0.0.1"),
                locationA,
                RouterType.EDGE).getId();
        var router = this.routerManagementGenericAdapter.retrieveRouter(routerId);
        assertEquals(routerId, router.getId());
    }

    @Test
    public void createAndAddRouter() {
        var id = Id.withId("b832ef4f-f894-4194-8feb-a99c2cd4be0c");
        var coreRouter = (CoreRouter)this.
                routerManagementGenericAdapter.retrieveRouter(id);
        var edgeRouter = this.
                routerManagementGenericAdapter.createRouter(
                Vendor.DLINK,
                Model.XYZ0001,
                IP.fromAddress("40.0.0.1"),
                locationA,
                RouterType.EDGE
        );
        var actualRouter = (CoreRouter)this.routerManagementGenericAdapter.addRouterToCoreRouter(edgeRouter,coreRouter);
        assertEquals(edgeRouter, actualRouter.getRouters().get(edgeRouter.getId()));
    }

    @Test
    public void removeRouter() {
        var routerId = Id.withId("b832ef4f-f894-4194-8feb-a99c2cd4be0b");
        var router = this.routerManagementGenericAdapter.removeRouter(routerId);
        assertEquals(null, router);
    }

    @Test
    public void removeRouterFromCoreRouter(){
        var routerId = Id.withId("b832ef4f-f894-4194-8feb-a99c2cd4be0a");
        var coreRouterId = Id.withId("b832ef4f-f894-4194-8feb-a99c2cd4be0c");
        this.routerManagementGenericAdapter.removeRouterFromCoreRouter(routerId, coreRouterId);
    }
}
