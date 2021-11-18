package dev.davivieira.framework.adapters.output.file;

import dev.davivieira.application.ports.output.RouterViewOutputPort;
import dev.davivieira.domain.entity.Router;
import dev.davivieira.domain.vo.RouterId;
import dev.davivieira.domain.vo.RouterType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class RouterViewFileAdapter implements RouterViewOutputPort {

    private static RouterViewFileAdapter instance;

    @Override
    public List<Router> fetchRouters() {
        return readFileAsString();
    }

    private static List<Router> readFileAsString() {
        List<Router> routers = new ArrayList<>();
        try (Stream<String> stream = new BufferedReader(
                new InputStreamReader(
                        RouterViewFileAdapter.class.getClassLoader().
                                getResourceAsStream("routers.txt"))).lines()) {
            stream.forEach(line ->{
                String[] routerEntry = line.split(";");
                var id = routerEntry[0];
                var type = routerEntry[1];
                Router router = new Router(RouterType.valueOf(type),RouterId.withId(id));
                routers.add(router);
            });
        } catch (Exception e){
            e.printStackTrace();
        }
        return routers;
    }

    private RouterViewFileAdapter() {
    }

    public static RouterViewFileAdapter getInstance() {
        if (instance == null) {
            instance = new RouterViewFileAdapter();
        }
        return instance;
    }
}
