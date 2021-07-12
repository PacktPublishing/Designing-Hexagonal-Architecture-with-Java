package dev.davivieira.framework.adapters.output.file;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.davivieira.application.ports.output.RouterNetworkOutputPort;
import dev.davivieira.domain.entity.Router;
import dev.davivieira.domain.vo.RouterId;
import dev.davivieira.framework.adapters.output.file.json.RouterJson;
import dev.davivieira.framework.adapters.output.file.mappers.RouterJsonFileMapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;;
import java.util.List;

public class RouterNetworkFileAdapter implements RouterNetworkOutputPort {

    private static RouterNetworkFileAdapter instance;

    private List<RouterJson> routers;

    private InputStream resource = getClass().getClassLoader().getResourceAsStream("inventory.json");

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Router fetchRouterById(RouterId routerId) {
        Router router = new Router();
        for(RouterJson routerJson: routers){
            if(routerJson.getRouterId().equals(routerId.getUUID())){
                    router = RouterJsonFileMapper.toDomain(routerJson);
                break;
            }
        }
        return router;
    }

    @Override
    public boolean persistRouter(Router router) {
        var routerJson = RouterJsonFileMapper.toJson(router);
        routers.add(routerJson);
        try {
            objectMapper.writeValue(new File("inventory.json"), routers);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    private void readJsonFile(){
        try {
            this.routers = objectMapper.
                    readValue(
                            resource,
                            new TypeReference<List<RouterJson>>(){});
            System.out.println(this.routers.get(0).getRouterId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private RouterNetworkFileAdapter() {
        readJsonFile();
    }

    public static RouterNetworkFileAdapter getInstance() {
        if (instance == null) {
            instance = new RouterNetworkFileAdapter();
        }
        return instance;
    }
}
