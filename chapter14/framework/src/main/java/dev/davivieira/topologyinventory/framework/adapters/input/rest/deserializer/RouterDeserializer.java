package dev.davivieira.topologyinventory.framework.adapters.input.rest.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import dev.davivieira.topologyinventory.domain.entity.CoreRouter;
import dev.davivieira.topologyinventory.domain.entity.EdgeRouter;
import dev.davivieira.topologyinventory.domain.entity.Router;
import dev.davivieira.topologyinventory.domain.entity.Switch;
import dev.davivieira.topologyinventory.domain.entity.factory.RouterFactory;
import dev.davivieira.topologyinventory.domain.vo.IP;
import dev.davivieira.topologyinventory.domain.vo.Id;
import dev.davivieira.topologyinventory.domain.vo.Model;
import dev.davivieira.topologyinventory.domain.vo.RouterType;
import dev.davivieira.topologyinventory.domain.vo.Vendor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static dev.davivieira.topologyinventory.framework.adapters.input.rest.deserializer.LocationDeserializer.getLocation;
import static dev.davivieira.topologyinventory.framework.adapters.input.rest.deserializer.SwitchDeserializer.getSwitchDeserialized;

public class RouterDeserializer extends StdDeserializer<Router> {

    public RouterDeserializer() {
        this(null);
    }

    public RouterDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Router deserialize(JsonParser jsonParser, DeserializationContext ctxt)
            throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        var id = node.get("id").get("uuid").asText();
        var vendor = node.get("vendor").asText();
        var model = node.get("model").asText();
        var ip = node.get("ip").get("ipAddress").asText();
        var location = node.get("location");
        var routerType = RouterType.valueOf(node.get("routerType").asText());
        var routersNode = node.get("routers");
        var switchesNode = node.get("switches");

        var router = RouterFactory.getRouter(
                Id.withId(id),
                Vendor.valueOf(vendor),
                Model.valueOf(model),
                IP.fromAddress(ip),
                getLocation(location),
                routerType);

        fetchChildRouters(routerType, routersNode, router);
        fetchChildSwitches(routerType, switchesNode, router);

        return router;
    }

    private void fetchChildRouters(RouterType routerType, JsonNode routersNode, Router router) throws IOException {
        Map<Id, Router> routers = new HashMap<>();
        if (routerType==RouterType.CORE && routers != null) {
            Iterator<String> childRouters = routersNode.fieldNames();
            while (childRouters.hasNext()) {
                String childRouter = childRouters.next();
                JsonNode routerJsonNode = routersNode.get(childRouter);
                var fetchedRouter = getRouterDeserialized(routerJsonNode.toString());
                routers.put(fetchedRouter.getId(), fetchedRouter);
            }
            ((CoreRouter)router).setRouters(routers);
        }
    }

    private void fetchChildSwitches(RouterType routerType, JsonNode switchesNode, Router router) throws IOException {
        Map<Id, Switch> switches = new HashMap<>();
        if (routerType==RouterType.EDGE && switches != null) {
            var childSwitches = switchesNode.fieldNames();
            while (childSwitches.hasNext()) {
                var childSwitch = childSwitches.next();
                var switchJsonNode = switchesNode.get(childSwitch);
                var fetchedSwitch = getSwitchDeserialized(switchJsonNode.toString());
                switches.put(fetchedSwitch.getId(), fetchedSwitch);
            }
            ((EdgeRouter)router).setSwitches(switches);
        }
    }

    public static Router getRouterDeserialized(String jsonStr) throws IOException {
        var mapper = new ObjectMapper();
        var module = new SimpleModule();
        module.addDeserializer(Router.class, new RouterDeserializer());
        mapper.registerModule(module);
        var router = mapper.readValue(jsonStr, Router.class);
        return router;
    }
}
