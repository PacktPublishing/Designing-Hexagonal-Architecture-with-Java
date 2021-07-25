package dev.davivieira.topologyinventory.framework;

import dev.davivieira.topologyinventory.domain.entity.CoreRouter;
import dev.davivieira.topologyinventory.domain.vo.*;
import dev.davivieira.topologyinventory.framework.adapters.input.generic.RouterManagementGenericAdapter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FrameworkTest extends FrameworkTestData {

    RouterManagementGenericAdapter routerManagementGenericAdapter;

    Id id;

    public FrameworkTest(){
        this.routerManagementGenericAdapter = new RouterManagementGenericAdapter();
        this.id = Id.withId("b832ef4f-f894-4194-8feb-a99c2cd4be0c");
        loadData();
    }

    @Test
    public void retrieveRouter(){
        var actualId = routerManagementGenericAdapter.
                retrieveRouter(id).getId().getUuid();
        assertEquals(id.getUuid(), actualId);
    }

    @Test
    public void createAndAddRouter(){
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
        this.routerManagementGenericAdapter.addRouterToCoreRouter(edgeRouter,coreRouter);
    }
}
