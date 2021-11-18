package dev.davivieira.topologyinventory.framework;

import dev.davivieira.topologyinventory.domain.entity.CoreRouter;
import dev.davivieira.topologyinventory.domain.vo.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RouterTest extends FrameworkTestData {

    public RouterTest() {
        loadPortsAndUseCases();
        loadData();
    }

    @Test
    public void retrieveRouter() {
        var id = Id.withId("b832ef4f-f894-4194-8feb-a99c2cd4be0c");
        var actualId = routerManagementGenericAdapter.
                retrieveRouter(id).getId();
        assertEquals(id, actualId);
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
    public void addRouterToCoreRouter() {
        var routerId = Id.withId("b832ef4f-f894-4194-8feb-a99c2cd4be0b");
        var coreRouterId = Id.withId("b832ef4f-f894-4194-8feb-a99c2cd4be0c");
        var actualRouter = (CoreRouter)this.routerManagementGenericAdapter.
               addRouterToCoreRouter(routerId,coreRouterId);
        assertEquals(routerId, actualRouter.getRouters().get(routerId).getId());
    }

    @Test
    public void removeRouterFromCoreRouter(){
        var routerId = Id.withId("b832ef4f-f894-4194-8feb-a99c2cd4be0a");
        var coreRouterId = Id.withId("b832ef4f-f894-4194-8feb-a99c2cd4be0c");
        var removedRouter = this.routerManagementGenericAdapter.
                removeRouterFromCoreRouter(routerId, coreRouterId);
        assertEquals(routerId, removedRouter.getId());
    }

    @Test
    public void removeRouter() {
        var routerId = Id.withId("b832ef4f-f894-4194-8feb-a99c2cd4be0b");
        var router = this.routerManagementGenericAdapter.removeRouter(routerId);
        assertEquals(null, router);
    }
}
