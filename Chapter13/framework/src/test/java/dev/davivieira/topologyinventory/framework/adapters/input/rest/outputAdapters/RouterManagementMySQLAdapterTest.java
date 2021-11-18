package dev.davivieira.topologyinventory.framework.adapters.input.rest.outputAdapters;

import dev.davivieira.topologyinventory.domain.entity.Router;
import dev.davivieira.topologyinventory.domain.entity.factory.RouterFactory;
import dev.davivieira.topologyinventory.domain.vo.IP;
import dev.davivieira.topologyinventory.domain.vo.Id;
import dev.davivieira.topologyinventory.domain.vo.Model;
import dev.davivieira.topologyinventory.domain.vo.RouterType;
import dev.davivieira.topologyinventory.domain.vo.Vendor;
import dev.davivieira.topologyinventory.framework.adapters.input.rest.RouterManagementAdapterTest;
import dev.davivieira.topologyinventory.framework.adapters.output.mysql.RouterManagementMySQLAdapter;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@QuarkusTest
public class RouterManagementMySQLAdapterTest {

    @InjectMock
    RouterManagementMySQLAdapter routerManagementMySQLAdapter;

    @Test
    public void testRetrieveRouter() {
        Router router = getRouter();
        Mockito.when(routerManagementMySQLAdapter.retrieveRouter(router.getId())).thenReturn(router);
        Router retrievedRouter = routerManagementMySQLAdapter.retrieveRouter(router.getId());
        Assertions.assertSame(router, retrievedRouter);
    }

    @Test
    public void testRemoveRouter() {
        Router router = getRouter();
        Mockito.when(routerManagementMySQLAdapter.removeRouter(router.getId())).thenReturn(router);
        Router removedRouter = routerManagementMySQLAdapter.removeRouter(router.getId());
        Assertions.assertSame(router, removedRouter);
    }

    @Test
    public void testPersistRouter() {
        Router router = getRouter();
        Mockito.when(routerManagementMySQLAdapter.persistRouter(router)).thenReturn(router);
        Router persistedRouter = routerManagementMySQLAdapter.persistRouter(router);
        Assertions.assertSame(router, persistedRouter);
    }

    private Router getRouter(){
        return RouterFactory.getRouter(
                Id.withoutId(),
                Vendor.CISCO,
                Model.XYZ0004,
                IP.fromAddress("10.0.0.1"),
                RouterManagementAdapterTest.createLocation("US"),
                RouterType.EDGE);
    }
}
